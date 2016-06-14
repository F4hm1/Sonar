package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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
        try {
            Interest[] v_sports = new Interest[2];
            Interest[] v_music = new Interest[3];
            Interest[] v_engines = new Interest[2];
            Interest[] v_techs = new Interest[2];
            int i_sports = 0;
            int i_music = 0;
            int i_engines = 0;
            int i_techs = 0;

            String risposta = response.body().string();
            Log.i("Interest response",risposta);
            interests = gson.fromJson(risposta,Interest[].class);
            for(int i = 0; i < interests.length; i++) {
                Log.i("interest", interests[i].toString());
                switch(interests[i].getCategory_id()){
                    case 1:
                        v_sports[i_sports]=interests[i];
                        i_sports++;
                        break;
                    case 2:
                        v_music[i_music]=interests[i];
                        i_music++;
                        break;
                    case 3:
                        v_engines[i_engines]=interests[i];
                        i_engines++;
                        break;
                    case 4:
                        v_techs[i_techs]=interests[i];
                        i_techs++;
                        break;
                }
            }
            final InterestListViewAdapter adapter1 = new InterestListViewAdapter(this,v_sports);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView) findViewById(R.id.lv_interests_sport);
                    //adapter.addAll(interests);
                    listView.setAdapter(adapter1);
                }
            });
            final InterestListViewAdapter adapter2 = new InterestListViewAdapter(this,v_music);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView) findViewById(R.id.lv_interests_music);
                    //adapter.addAll(interests);
                    listView.setAdapter(adapter2);
                }
            });
            final InterestListViewAdapter adapter3 = new InterestListViewAdapter(this,v_engines);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView) findViewById(R.id.lv_interests_engines);
                    //adapter.addAll(interests);
                    listView.setAdapter(adapter3);
                }
            });
            final InterestListViewAdapter adapter4 = new InterestListViewAdapter(this,v_techs);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView) findViewById(R.id.lv_interests_tech);
                    //adapter.addAll(interests);
                    listView.setAdapter(adapter4);
                }
            });

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

