package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;

import java.io.IOException;

import com.squareup.okhttp.*;
import okio.BufferedSink;

/**
 * Created by Alessandro on 31/03/2016.
 */
public class HttpHandler extends AsyncTask<String, String, String> {

    private static final String API_BASE_URL="http://cnvcrew.ddns.net/api/";                        /* Punto di partenza per tutti gli url */

    @Override
    protected String doInBackground(String... params){
        String url = API_BASE_URL + params[0];
        OkHttpClient http = new OkHttpClient();
        Response response = null;
        /*RequestBody form = new FormEncodingBuilder().add("username", "lapse").add("password", "lapse").build();
        Request request = new Request.Builder()
                .url(url)
                .post(form)
                .build();
        Log.i("request",request.toString());*/
        RequestBody form = new FormEncodingBuilder()
                .add("login", params[1])
                .build();
        Request request = new Request.Builder()
                .url("http://cnvcrew.ddns.net/api/login.php")
                .post(form)
                .build();
        try {
            Log.i("request body", form.toString());
            response = http.newCall(request).execute();
            Log.i("response",response.toString());
            Log.i("response body",response.body().string());
        }catch(IOException e){

        }
        return response.toString();
    }

    public void connect(String url, String data){
        this.execute(url,data);
    }
}
