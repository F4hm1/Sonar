package it.cnvcrew.sonar;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    //private GoogleApiClient gAPIClient;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gAPIClient = PositionFragment.gAPIClient;*/
    }

   /* @Override
    public void onStart(){
        super.onStart();
        //Log.i("Position","onStart");
        gAPIClient.connect();
    }*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.i("Permission","Working");
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.i("Permission","Granted");
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), this);
                    Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            gAPIClient);
                }catch(SecurityException e){

                }
            }else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},2);
                Log.i("Perimission","requesting");
            }
        }else{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.i("Permission","Already granted");
                LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), this);
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        gAPIClient);
            }
        }

      */
        LatLng userPosition = new LatLng(getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0));
        mMap.addMarker(new MarkerOptions().position(userPosition).title("Marker in last known position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
