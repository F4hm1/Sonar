package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class InterestsActivity extends AppCompatActivity implements ResponseListener{

    Gson gson = new Gson();
    Interest[] interests = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        InterestHandler handler = new InterestHandler();
        handler.addListener(this);
        Log.i("interest request",gson.toJson(MyNavigationDrawer.loggedUser));
        handler.connect(gson.toJson(MyNavigationDrawer.loggedUser));

        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.interest_rel_layout);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_interests, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.btn_interests_refresh:
                Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
                Log.i("interest refresh",gson.toJson(MyNavigationDrawer.loggedUser));
                InterestHandler handler = new InterestHandler();
                handler.addListener(this);
                handler.connect(gson.toJson(MyNavigationDrawer.loggedUser));
                break;
            case R.id.btn_interests_submit:
                Toast.makeText(this, "Submit", Toast.LENGTH_LONG).show();

                break;
        }
        return true;
    }

    public void setListview(){

    }

    @Override
    public void onApiResponseReceived(Response response) {
        Category[] categories;
        try {
            String risposta = response.body().string();
            Log.i("Interest response",risposta);
            interests = gson.fromJson(risposta,Interest[].class);
            int nCategories = 0;
            try{
                JSONArray userArray = new JSONArray(risposta);
                JSONObject userObject = userArray.getJSONObject(userArray.length()-1);
                nCategories = userObject.getInt("numero");
                Log.i("numero",String.valueOf(nCategories));
            }catch(JSONException e){
                e.printStackTrace();
            }
            if(nCategories == 0){
                this.finish();
            }
            categories = new Category[nCategories];
            for(int i = 0; i < nCategories; i++) {
                categories[i] = new Category(i+1);
                for (int j = 0; j<interests.length; j++){
                    if(interests[j].getCategory_id() == i+1){
                        categories[i].setInterests(interests[j]);
                        Log.i("interest", interests[i].toString());
                    }
                }
            }

        }catch(IOException e){
            Log.e("Interest response error",e.toString());
        }
    }
}

class InterestHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;

        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("user", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_GET_INTERESTS)
                .post(form)
                .build();
        try {
            Log.i("listener reference",listener.toString());
            //this.publishProgress("richiesta");
            response = http.newCall(request).execute();
            this.publishProgress("risposta");
            listener.onApiResponseReceived(response);
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

