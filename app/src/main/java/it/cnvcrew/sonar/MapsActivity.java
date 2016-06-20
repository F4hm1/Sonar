package it.cnvcrew.sonar;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import ch.hsr.geohash.GeoHash;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        LocationSource.OnLocationChangedListener, LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient gAPIClient;
    private Location lastLocation;
    private PositionHandler handler;
    private Gson gson;
    private GeoHash hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate","-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gAPIClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        gson = new Gson();
    }

    protected void onStart() {
        Log.i("onStart","-");
        gAPIClient.connect();
        super.onStart();
    }

    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(gAPIClient,this);
        gAPIClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("onConnected","-");
        checkPermissions();
    }

    private void checkPermissions(){
        Log.i("checkPermissions","-");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 420);
        } else {
            updateLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 420: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    updateLocation();

                } else {
                    Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.permission_denied_dialog);
                    dialog.setTitle("ACCETTA DIOCAN");
                    dialog.show();
                    this.finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /*private Location getPosition(){
        Log.i("getPosition","-");
        LocationRequest lr = new LocationRequest().setInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, lr,this);
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                gAPIClient);
        Log.i("location",lastLocation.toString());

        if (lastLocation != null) {
            updateLocation();
        }
        return lastLocation;
    }*/

    @Override
    public void onConnectionSuspended(int i) {

    }

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
        Log.i("onMapReady","-");
        mMap = googleMap;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("onLocationChanged","-");
        mMap.clear();
        LatLng pos = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(pos).title(getResources().getString(R.string.your_position)));
        mMap.addCircle(new CircleOptions()
                .center(pos)
                .radius(1000)
                .fillColor(Color.argb(80,255,152,0))
                .strokeColor(Color.argb(200,255,152,0))
                .strokeWidth(5)
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14.5f));
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        hash = GeoHash.withCharacterPrecision(location.getLatitude(),location.getLongitude(),7);
        Position position = new Position(MyNavigationDrawer.loggedUser.getId(),location.getLatitude(),location.getLongitude(), hash.toBase32());
        handler = new PositionHandler();
        String json = gson.toJson(position);
        Log.i("JSON",json);
        handler.connect(json);
    }

    public void updateLocation(){
        Log.i("updateLocation","-");
        LocationRequest lr = new LocationRequest().setInterval(10000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(gAPIClient, lr,this);
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                gAPIClient);
        Log.i("location",location.toString());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14.5f));
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


class PositionHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;
        Log.i("Request",params[0]);
        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("position", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_POSITION_URL)
                .post(form)
                .build();
        try {
            Log.i("Request",request.toString());
            //this.publishProgress("richiesta");
            response = http.newCall(request).execute();
            //this.publishProgress("risposta");
            //listener.onApiResponseReceived(response);
            Log.i("response",response.toString());
            Log.i("response body",response.body().string());
            ritorno = response.toString();
        }catch(UnknownHostException e){
            Log.e("Request exception","Host sconosciuto");
            this.publishProgress("HOST SCONOSCIUTO");
            ritorno = "unknownhost";
        }catch(IOException e){
            Log.e("Request exception","IOException");
            this.publishProgress("Errore di I/O");
            ritorno = "ioexception";
        }
        return ritorno;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public void connect(String data){
        this.execute(data);
    }

    void addListener(ResponseListener listenerToAdd){
        listener=listenerToAdd;
    }

}
