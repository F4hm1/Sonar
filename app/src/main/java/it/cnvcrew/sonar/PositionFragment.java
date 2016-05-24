package it.cnvcrew.sonar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.location.LocationManager;

import com.lambdaworks.redis.*;

public class PositionFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, android.location.LocationListener, com.google.android.gms.location.LocationListener {

    //TODO improve permission handling
    private GoogleApiClient gAPIClient;
    private Location lastLocation;
    private View layout;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION_ACCESS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Position","onCreate");
        View v = inflater.inflate(R.layout.fragment_position, container, false);
        gAPIClient = new GoogleApiClient.Builder(this.getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        ((Button) v.findViewById(R.id.btn_send_position_high)).setOnClickListener(this);
        ((Button) v.findViewById(R.id.btn_send_position_low)).setOnClickListener(this);
        layout=v;

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("Position","onStart");
        gAPIClient.connect();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("Position","onStop");
        gAPIClient.disconnect();
    }

    @Override
    public void onClick (View v){
        /* LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, ); */
        Log.i("Position","onClick");
        LocationRequest lRequest;
        try {
            Log.i("Position onClick","try");
            if(((Button) v).getId()==R.id.btn_send_position_high) {
                lRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationManager lManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
                lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, this);
                Log.i("Position mode","High accuracy");
            }
            else{
                lRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER);
                Log.i("Position mode","Low Power");
            }
            if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Log.i("Permission","Working");
                if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
                    Log.i("Permission","Granted");
                    try {
                        LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), this);
                        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                gAPIClient);
                        RedisClient redis = new RedisClient("http://127.0.0.1", 8888);
                    }catch(SecurityException e){

                    }
                }else{
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION_ACCESS);
                    Log.i("Perimission","requesting");
                }
            }else{
                if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Log.i("Permission","Already granted");
                    LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY), this);
                    lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            gAPIClient);
                }
            }


        }catch(Exception e){
            Log.i("Position onClick","catch");
            e.printStackTrace();
            Toast.makeText(this.getContext(),"GIV PERMISSIONz PLZ",Toast.LENGTH_SHORT).show();
        }
        if (lastLocation != null) {
            Log.i("Position onClick","if");
            TextView mLatitudeText = (TextView) layout.findViewById(R.id.tv_latitude);
            TextView mLongitudeText = (TextView) layout.findViewById(R.id.tv_longitude);
            TextView mAccuracyText = (TextView) layout.findViewById(R.id.tv_accuracy);
            mLatitudeText.setText(String.valueOf(lastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(lastLocation.getLongitude()));
            mAccuracyText.setText(String.valueOf(lastLocation.getAccuracy()));
        }
    }

    //@Override
    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION_ACCESS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        float[] results = new float[5];
        Location.distanceBetween(location.getLatitude(),location.getLongitude(),45.707759, 12.26146, results);
        Log.i("Latitude", String.valueOf(location.getLatitude()));
        Log.i("Longitude", String.valueOf(location.getLongitude()));
        Log.i("Distance", String.valueOf(results[0]));
        Log.i("Net Distance", String.valueOf(results[0]-location.getAccuracy()));
        ((TextView) this.getActivity().findViewById(R.id.tv_net_accuracy)).setText(String.valueOf(results[0]-location.getAccuracy()));
        if(results[0]<(location.getAccuracy()+1000)){
            ((TextView) this.getActivity().findViewById(R.id.tv_dovesei)).setText("SCUOLA");
        }
        else{
            ((TextView) this.getActivity().findViewById(R.id.tv_dovesei)).setText("SCONOSCIUTO");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
