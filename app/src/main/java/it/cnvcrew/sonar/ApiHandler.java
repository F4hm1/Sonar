package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.net.URLEncoder;

/**
 * Created by Alessandro on 06/03/2016.
 */
public class ApiHandler extends AsyncTask<String,String,String> {

    private static final String API_BASE_URL="http://cnvcrew.ddns.net/api/";                        /* Punto di partenza per tutti gli url */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");        /* Roba di OkHttp, MIME qualcosa */

    @Override
    protected String doInBackground(String... data) {                                               /* Metodo da implementare per AsyncTask; String... = # non specificato di argomenti */
        String url = API_BASE_URL + data[0];                                                        /* Compone l'url completo (data[0] è la pagina specifica) */
        String json = "'" + data[1] + "'";                                                                      /* data[1] è il JSON */
        String encodedRequest = null;
        String encodedJson = null;
        Log.d("Unencoded request", data[0]+"?login=" + json);
        try {
            if (json != null) {
                encodedJson = URLEncoder.encode(json, "utf-8");                                     /* La richiesta va mandata usando caratteri web-safe es. %20 al posto dello spazio */
                encodedRequest = url + "?login=" + encodedJson;                                             /* Unisce url e JSON per formare la richiesta completa (verrà cambiato in POST) */
            }
            Log.d("Encoded request", encodedRequest);
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, encodedJson);                               /* Boh... è così, crea il corpo della richiesta */
            Request request = new Request.Builder().url(url).post(body).build();                    /* Per POST sarà [...].post(body) al posto di .method */
            Response response = client.newCall(request).execute();                                  /* Esegue la richiesta e si salva la risposta */
            Log.i("response", response.body().string());
            Log.i("method", request.method());
            Log.i("request", request.toString());
            Log.i("url", url);
            Log.i("body", response.toString());
        }
        catch(Exception e){
            Log.e("doInBackground exception", e.toString());
        }
        return null;
    }

    public void toApi(String url, String json){
        try{
            this.execute(url, json);
        }catch(Exception e){
            Log.e("toApi exception", e.toString());
        }
    }
}
