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
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Shared.SessionData;

public class MapsActivity extends FragmentActivity
        implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMapClickListener,
        OnMarkerClickListener,
        ResultCallback<Status>,
        GeofenceResultReceiver.GeofenceReceiver {

    // Small Container Class
    private class BoardAndMarkerOptions{
        Board b;
        MarkerOptions m;
    }

    // Logger Tag
    private static final String TAG = "Maps";

    // Location
    private GoogleApiClient mGoogleApiClient;
    private LatLng mDefaultLocationHalifax = new LatLng(44.6488, 63.5752);
    private Location mLastKnownLocation = null;
    LocationRequest mLocationRequest;

    // Markers
    public static int MARKER_RADIUS = 3 * SessionData.posterSearchRadiusInMeters;
    public final int MARKER_PRUNE_MARKERS = 1234;
    List<Marker> shownMarkerList = new ArrayList<>();

    // GeoFencing
    public static int GEOFENCE_RADIUS_i = MARKER_RADIUS/2;
    public static float GEOFENCE_RADIUS_f = (float)GEOFENCE_RADIUS_i;
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

    // Maps
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private static final float DEFAULT_ZOOM = 16.4f;
    private static final float MAX_ZOOM = 18.0f; // For restricting access
    private static final float MIN_ZOOM = 15.0f;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 67;
    public double MAP_PAN_RESTRICTION_RADIUS = (double)GEOFENCE_RADIUS_f * Math.sqrt(2.0);


    Handler uiHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            Location loc = (Location)msg.obj;
            pruneOutOfRangeMarkers(getOldMarkers(), getNewMarkers(loc, MARKER_RADIUS));

        }
    };

    // Database
    final DatabaseHandler db = new DatabaseHandler(this);


    /* Implementations; ideas for the map:
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
        mMap.setMinZoomPreference(MIN_ZOOM);
        mMap.setMaxZoomPreference(MAX_ZOOM);
        setMapStyling(mMap);
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
            buildGeoFence(mLastKnownLocation);
            addInitialMarkers(mLastKnownLocation);
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
        if(mGoogleApiClient != null && !mGoogleApiClient.isConnected()){
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
        // Set camera panning bounds: restrict the user's vision
        mMap.setLatLngBoundsForCameraTarget(
                new LatLngBounds(
                        SphericalUtil.computeOffset(currLocation, MAP_PAN_RESTRICTION_RADIUS, 225),
                        SphericalUtil.computeOffset(currLocation, MAP_PAN_RESTRICTION_RADIUS, 45)
                )
        );
        // Update Radius data from the session data
        if(MARKER_RADIUS != SessionData.posterSearchRadiusInMeters){
            MARKER_RADIUS = SessionData.posterSearchRadiusInMeters;
            GEOFENCE_RADIUS_i = MARKER_RADIUS/2;
            GEOFENCE_RADIUS_f = (float)GEOFENCE_RADIUS_i;
            MAP_PAN_RESTRICTION_RADIUS = (double)GEOFENCE_RADIUS_f * Math.sqrt(2);
        }
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

    public List<BoardAndMarkerOptions> getNewMarkers(Location loc, int radius){
        // make a database call and build up a list of boards, based on loc
        // then build a list of markers from the boards; markers will have the boards as tags
        List<Board> boardList = db.searchAllBoardsWithinMetersOfGivenLatitudeLongitude(
                radius, loc.getLatitude(), loc.getLongitude());
        List<BoardAndMarkerOptions> list = new ArrayList<>();
        for(Board board : boardList){
            // Build a object of both a board and a set of MarkerOptions
            //TODO: add in the icon and anchors
            MarkerOptions mo = new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.telphone_pole_maps_icon))
                    //.anchor()
                    .draggable(false)
                    .visible(true);
            BoardAndMarkerOptions bmo = new BoardAndMarkerOptions();
            bmo.b = board;
            bmo.m = mo;
            list.add(bmo);
        }
        return list;
    }

    public List<Marker> getOldMarkers(){
        // get all of the markers previously assigned;
        return shownMarkerList;
    }

    public void pruneOutOfRangeMarkers(List<Marker> oldMarkers, List<BoardAndMarkerOptions> newMarkers){
        // Compare the tags of the previously lain markers to the boards in the newMarker
        Log.d(TAG, "There are " + oldMarkers.size() + " OldMarkers, and " + newMarkers.size() + " NewMarkers in range");
        List<Marker> toBeKept = new ArrayList<>();
        for(BoardAndMarkerOptions bmo : newMarkers){
            Board newBoard = bmo.b;
            MarkerOptions newOptions = bmo.m;
            for(Marker m : oldMarkers){
                Board oldBoard = (Board)m.getTag();
                // If the board is a duplicate of the other
                if(oldBoard.getId() == newBoard.getId()){
                    toBeKept.add(m);
                    oldMarkers.remove(m);
                    m.remove();
                    break;
                }
            }
            // Not found in the Old Board list
            Marker mark = mMap.addMarker(newOptions);
            mark.setTag(newBoard);
            toBeKept.add(mark);
        }

        shownMarkerList = toBeKept;
    }

    public void addInitialMarkers(Location loc){
        List<Board> boardList = db.searchAllBoardsWithinMetersOfGivenLatitudeLongitude(
                MARKER_RADIUS, loc.getLatitude(), loc.getLongitude());
        Log.d(TAG, "There are " + boardList.size() + " boards in range");
        for(Board b : boardList){
            //TODO: add in the icon and anchors
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.telphone_pole_maps_icon))
                    .visible(true)
                    .draggable(false)
            );
            m.setTag(b);
            shownMarkerList.add(m);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "YOU CLICKED A MARKER!", Toast.LENGTH_LONG).show();
        return false;
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
        // Remove the previous fence; continue in the callback
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                getGeofencePendingIntent()
        ).then(new ResultTransform<Status, Status>(){
            @Nullable
            @Override
            public PendingResult<Status> onSuccess(@NonNull Status status){
                // Callback on the success of the Removal, done in the background
                // Add a new Geofence at the current location
                uiHandler.obtainMessage(MARKER_PRUNE_MARKERS, mLastKnownLocation).sendToTarget();
                Log.d(TAG, "REMOVAL SUCCESS");
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
                //System.gc(); // End of the geofence handling, so clean up the disposed of Markers
                return null;
            }

            @NonNull
            @Override
            public Status onFailure(@NonNull Status status) {
                //Toast.makeText(MapsActivity.this, "Error in Building new Geofence", Toast.LENGTH_LONG).show();
                Log.d(TAG, "NOT AWESOME");
                return super.onFailure(status);
            }
        });
    }
}
