package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

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

    public void setListview(){

    }

    @Override
    public void onApiResponseReceived(Response response) {
        try {
            String risposta = response.body().string();
            Log.i("Interest response",risposta);
            interests = gson.fromJson(risposta,Interest[].class);
            for(int i = 0; i < interests.length; i++) {
                Log.i("interest", interests[i].toString());
            }
            final InterestListViewAdapter adapter = new InterestListViewAdapter(this,interests);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView) findViewById(R.id.lv_interest);
                    //adapter.addAll(interests);
                    listView.setAdapter(adapter);
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

