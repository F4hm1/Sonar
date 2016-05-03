package it.cnvcrew.sonar;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class PositionFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private GoogleApiClient gAPIClient;
    private Location lastLocation;
    private View layout;

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
        ((Button) v.findViewById(R.id.btn_send_position)).setOnClickListener(this);
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
        try {
            Log.i("Position onClick","try");
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    gAPIClient);
        }catch(SecurityException e){
            Log.i("Position onClick","catch");
            e.printStackTrace();
            Toast.makeText(this.getContext(),"GIV PERMISSIONz PLZ",Toast.LENGTH_SHORT).show();
        }
        if (lastLocation != null) {
            Log.i("Position onClick","if");
            TextView mLatitudeText = (TextView) layout.findViewById(R.id.tv_latitude);
            TextView mLongitudeText = (TextView) layout.findViewById(R.id.tv_longitude);
            mLatitudeText.setText(String.valueOf(lastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(lastLocation.getLongitude()));
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

}
