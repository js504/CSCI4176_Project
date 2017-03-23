package in.geobullet.csci_4176_project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class MapsActivity extends FragmentActivity
        implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnMapClickListener
{

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
    private static final float DEFAULT_ZOOM = 16.6f;
    private static final float MAX_ZOOM = 18.0f;
    private static final float MIN_ZOOM = 15.0f;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 67;

    LocationRequest mLocationRequest;

    // Current location
    private Location mLastKnownLocation;

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
     * >
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the View to be the map
        setContentView(R.layout.activity_maps);

        // Check for permissions
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get Permissions:
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }else{
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        setMapStyling(googleMap);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocationHalifax));
    }

    // If desired, can add different styles depending on the time
    private void setMapStyling(GoogleMap googleMap){
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_styles_day));
    }

    // handle Permission Changes
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:{
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){
                        if(mGoogleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }else{
                    Toast.makeText(this, "Location Permissions Denied; Please Enable to Use Maps Properly", Toast.LENGTH_LONG).show();
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
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this
            );
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

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }else{
                ActivityCompat.requestPermissions(
                        this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onLocationChanged(Location location){
        mLastKnownLocation = location;
        // move the User Marker

        LatLng currLocation = new LatLng(
                mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, DEFAULT_ZOOM));

        //if(mGoogleApiClient != null){
        //    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        //}

    }
}
