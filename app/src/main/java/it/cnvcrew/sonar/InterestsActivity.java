package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        GetInterestHandler handler = new GetInterestHandler();
        handler.addListener(this);
        Log.i("interest request",gson.toJson(MyNavigationDrawer.loggedUser));
        handler.connect(gson.toJson(MyNavigationDrawer.loggedUser));
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
                GetInterestHandler getHandler = new GetInterestHandler();
                getHandler.addListener(this);
                getHandler.connect(gson.toJson(MyNavigationDrawer.loggedUser));
                break;
            case R.id.btn_interests_submit:
                Toast.makeText(this, "Submit", Toast.LENGTH_LONG).show();
                SendInterestHandler sendHandler= new SendInterestHandler();
                String json = gson.toJson(interests);
                JSONObject object = null;
                JSONArray array = null;
                try{
                    array = new JSONArray(json);
                    object = new JSONObject();
                    object.put("interests",array);
                    object.put("user_id",MyNavigationDrawer.loggedUser.getId());
                }catch (JSONException e){
                    e.printStackTrace();
                }
                json = object.toString();
                sendHandler.addListener(this);
                sendHandler.connect(json);
                break;
        }
        return true;
    }

    @Override
    public void onApiResponseReceived(Response response) {
        final Category[] categories;
        try {
            String risposta = response.body().string();
            Log.i("Interest response",risposta);
            JSONArray array = new JSONArray(risposta);
            JSONObject object = array.getJSONObject(array.length() - 1);
            Log.i("operation",object.getString("operation"));
            if(object.getString("operation").equals("get")) {

                Log.i("Interest","getting");
                interests = gson.fromJson(risposta, Interest[].class);
                int nCategories = 0;
                try {
                    JSONArray categoryArray = new JSONArray(risposta);
                    JSONObject userObject = categoryArray.getJSONObject(categoryArray.length() - 2);
                    nCategories = userObject.getInt("numero");
                    Log.i("numero", String.valueOf(nCategories));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (nCategories == 0) {
                    this.finish();
                }
                categories = new Category[nCategories];
                for(int i = 0; i < nCategories; i++) {
                    categories[i] = new Category(i + 1);
                }

                for(int i = 0; i < interests.length; i++){

                    for(int j = 0; j < nCategories; j++){
                        if(interests[i].getCategory_id() == categories[j].getId()){
                            categories[j].setName(interests[i].getCategory_name());
                            categories[j].setInterests(interests[i]);
                        }
                    }
                }

                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_category_interests);
                final CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categories, getApplicationContext());
                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
            else{
                Log.i("Interests","setting");
                for(int i = 0; i < interests.length; i++) {
                    interests[i].setHas_changed(false);
                }
            }
        }catch(Exception e){
            Log.e("Interest response error",e.toString());
        }
    }
}

class GetInterestHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;
        Log.i("Request",params[0]);
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


class SendInterestHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;
        Log.i("Request",params[0]);
        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("interests", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_SET_INTERESTS)
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
