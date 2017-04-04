package in.geobullet.csci_4176_project;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Shared.SessionData;

public class MapsActivity extends FragmentActivity
        implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMapClickListener,
        ResultCallback<Status>,
        GeofenceResultReceiver.GeofenceReceiver {

    // Logger Tag
    private static final String TAG = "Maps";

    // Map instance
    private GoogleMap mMap;

    // Camera Position
    private CameraPosition mCameraPosition;

    // Play services API client
    private GoogleApiClient mGoogleApiClient;

    // Defaults for when location services not enabled Properly
    private LatLng mDefaultLocationHalifax = new LatLng(44.6488, 63.5752);
    private static final float DEFAULT_ZOOM = 16.4f;
    private static final float MAX_ZOOM = 18.0f; // For restricting access
    private static final float MIN_ZOOM = 15.0f;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 67;

    public static final float GEOFENCE_RADIUS_f = 50.0f;
    public static final int GEOFENCE_RADIUS_i = 25;
    public static final int GEOFENCE_RESPONSIVENESS = 1000;
    public static final long GEOFENCE_EXPIRATION = 1000 * 60 * 2; // 120 seconds
    public static final int GEOFENCE_LOITER_TIME = 1000 * 5; // 5 seconds
    public static final String GEOFENCE_REQ_ID = "GEOFENCE_REQUEST_ID";
    private PendingIntent geofencePendingIntent = null;
    private boolean geofenceEnable = false;
    public GeofenceResultReceiver resultReceiver = null;
    public Geofence currentFence = null;
    public Location currentFenceLocation = null;
    public boolean fenceSet = false;
    public final int MARKER_RADIUS = 3 * GEOFENCE_RADIUS_i;

    LocationRequest mLocationRequest;

    // Current location
    private Location mLastKnownLocation = null;

    // Need a Marker array to store the currently pulled markers
    Marker[] markerArray;
    // Needs an API to pull marker id's using a current location
    // Needs an API to pull board URLs using the marker id's


    /* Implementations; ideas for the map:
     * > Geo-fenced pole-fetching
     *      When a user leaves a set radius (set at the last fetch), redraw the geo-fence, and
     *      fetch the poles in the radius of the current geofence.
     * > Pan-and-Zoom on pole-click
     *      When a user clicks a pole, pan the camera over to the marker, and zoom down to it
     *      as the pole loads. Serves as an animated loading bar, effectively. Can launch the
     *      db calls at animation start, and then, on load, context switch to the board view.
     * > Map click => prompt to add new poster/board near there
     *      When a user clicks the map, prompt the user to add a board in that location.
     *      Maybe by adding a "shadow" icon of a pole or poster in the location, or something
     *      similar.
     * >
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the View to be the map
        setContentView(R.layout.activity_maps);

        // Check for permissions
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        resultReceiver = new GeofenceResultReceiver(new Handler());
        resultReceiver.setGFReceiver(this);
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get Permissions:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        setMapStyling(googleMap);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocationHalifax));
    }

    // If desired, can add different styles depending on the time
    private void setMapStyling(GoogleMap googleMap) {
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_styles_day));
    }

    // handle Permission Changes
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        // Get the last known location
                        if (mLastKnownLocation == null) {
                            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        }
                        if(mGoogleApiClient != null && !mGoogleApiClient.isConnected()){
                            mGoogleApiClient.connect();
                        }
                    }
                } else {
                    Toast.makeText(this, "Location Permissions Denied;\nPlease Enable to Use Maps Properly", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // Connection Handlers
    @Override
    public void onConnected(Bundle connectionHint) {
        // Get current Location
        mLocationRequest = new LocationRequest()
                .setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this
            );
            // Get the last known location
            if (mLastKnownLocation == null) {
                mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
            //if(!fenceSet){
             //   fenceSet = true;
                buildGeoFence(mLastKnownLocation);
            //}
            // Populate with Markers
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null){
            mGoogleApiClient.disconnect();
        }
        if(resultReceiver != null){
            resultReceiver.setGFReceiver(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
        if(resultReceiver != null){
            resultReceiver.setGFReceiver(this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Handler for case where connection fails
        Log.d(TAG, "Connection Failed: " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Connection Suspended");
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastKnownLocation = location;
        SessionData.location = location;
        // move the User Marker

        LatLng currLocation = new LatLng(
                mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, DEFAULT_ZOOM));

        //if(mGoogleApiClient != null){
        //    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        //}

    }

    public PendingResult<Status> buildGeoFence(Location loc) {
        // Only set up Exiting as the Geofence transition: only care abo0ut a user leaving a fence.
        if (loc == null) return null;
        Geofence fence = new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setExpirationDuration(GEOFENCE_EXPIRATION)
                .setCircularRegion(loc.getLatitude(), loc.getLongitude(), GEOFENCE_RADIUS_i)
                .setNotificationResponsiveness(GEOFENCE_RESPONSIVENESS)
                //.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL) // testing
                .setLoiteringDelay(GEOFENCE_LOITER_TIME)
                .build();
        currentFence = fence;
        currentFenceLocation = loc;
        GeofencingRequest fenceRequest = new GeofencingRequest.Builder()
                .addGeofence(fence)
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_DWELL) // Testing
                .build();
        try {
            return LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    fenceRequest,
                    getGeofencePendingIntent()
            );

        } catch (SecurityException e) {
            Log.d(TAG, "can't do this, As not allowed");
            e.printStackTrace();
            return null;
        }
    }

    public List<Marker> getNewMarkers(Location loc, int radius){
        // make a database call and build up a list of boards, based on loc
        // then build a list of markers from the boards; markers will have the boards as tags
        List<Marker> markerList = null;
        return markerList;
    }

    public List<Marker> getOldMarkers(){
        // get all of the markers previously assigned;
        // Should be stored in an array, probably.
        List<Marker> markerList = null;
        return markerList;
    }

    public void pruneOutOfRangeMarkers(List<Marker> oldMarkers, List<Marker> newMarkers){

    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceIntentHandler.class);
        intent.putExtra(GeofenceResultReceiver.TAG, resultReceiver);
        geofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            // set that geofence has been enabled
            geofenceEnable = true;
            Log.d(TAG, "Geofence enabled!");
        }else{
            Log.d(TAG, "Returned, not success?");
            Log.d(TAG, "Status code = " + status.getStatusCode());
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        // The received code comes back strange. Not sure what is going on there.
        // However, it reliably comes back when it is supposed to.
        // Therefore, as long as we know that there is only a single geofence active at a time,
        // we can assume that any received data from the intent handler is correct.
        // So: when a message is received, get the ReqId from the constant, GEOFENCE_REQUEST_ID
        // The location is stored in the currentFenceLocation. Other data is in the currentFence.

        // Tasks:
        // > Remove the

        fenceSet = false;
        currentFence = null;
        Toast.makeText(this, "Geofence broken!", Toast.LENGTH_LONG).show();
        // Remove the last fence; continue in the callback
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                getGeofencePendingIntent()
        ).then(new ResultTransform<Status, Status>(){
            @Nullable
            @Override
            public PendingResult<Status> onSuccess(Status status){
                // Callback on the success of the Removal, done in the background
                // Add a new Geofence at the current location
                pruneOutOfRangeMarkers(
                        getOldMarkers(),
                        getNewMarkers(mLastKnownLocation, MARKER_RADIUS)
                );
                Log.d(TAG, "Removal Success");
                return buildGeoFence(mLastKnownLocation);
            }

            @NonNull
            @Override
            public Status onFailure(@NonNull Status status) {
                // Failure to remove
                Log.d(TAG, "REMOVAL FAILURE");
                //Toast.makeText(MapsActivity.this, "Error in removing the Geofence", Toast.LENGTH_LONG).show();
                return super.onFailure(status);
            }
        }).then(new ResultTransform<Status, Result>() {
            @Nullable
            @Override
            public PendingResult<Result> onSuccess(@NonNull Status status) {
                // callback for Adding the new Geofence at mLastKnownLocation
                //Toast.makeText(MapsActivity.this, "GeoFence rebuilt!", Toast.LENGTH_LONG).show();
                Log.d(TAG, "TOTALLY AWESOME");
                return null;
            }

            @NonNull
            @Override
            public Status onFailure(@NonNull Status status) {
                //Toast.makeText(MapsActivity.this, "Error in Building new Geofence", Toast.LENGTH_LONG).show();
                Log.d(TAG, "NOT AWSOME");
                return super.onFailure(status);
            }
        });
    }
}
