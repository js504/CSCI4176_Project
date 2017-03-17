package in.geobullet.csci_4176_project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements  OnMapReadyCallback,
                    GoogleApiClient.ConnectionCallbacks,
                    GoogleApiClient.OnConnectionFailedListener
{

    // Logger Tag
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final String PTAG = "Permissions";

    // Map instance
    private GoogleMap mMap;

    // Camera Position
    private CameraPosition mCameraPosition;

    // Play services API client (Places API, Fused Location provider API)
    private GoogleApiClient mGoogleApiClient;

    // Defaults for when location services not enabled Properly
    private LatLng mDefaultLocationHalifax = new LatLng(44.6488, 63.5752);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // Current location
    private Location mLastKnownLocation;

    // Keys for storing activity state
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve saved location/camera states from saved instance
        if(savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // set the View to be the map
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Builds the Map up

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

        updateLocationUI();

//        LatLng coords = new LatLng(44.6488, 63.5752);
        LatLng coords = getDeviceLocation();

        mMap.addMarker(new MarkerOptions().position(coords).title("Halifax is here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
    }

    // Save the State on context switch
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    // todo https://developers.google.com/maps/documentation/android-api/location


    // handle Permission Changes
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch(requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else if (grantResults.length > 0) {
                }
            }
        }
        updateLocationUI();
    }

    // Connection Handlers
    @Override
    public void onConnected(Bundle connectionHint) {
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


    public LatLng getDeviceLocation() {

        // First, request the permissions for

        LatLng coords = null;

        int permissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
        }

        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        // Get the coords that the system will be using as the current

        if (mCameraPosition != null) {

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
            coords = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

        } else if (mLastKnownLocation != null) {

            coords = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, DEFAULT_ZOOM));

        } else {
            Log.d(PTAG, "Location null, using defaults");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocationHalifax, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        return coords;
    }

    public void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        // Request the location permissions, so to know if we can do a thing

        int permissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            Log.d(PTAG, "inside updateLocationUI, granted");
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
            Log.d(PTAG, "inside updateLocationUI, requesting permission");
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

}
