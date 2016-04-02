package it.cnvcrew.sonar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

interface ResponseListener {
    void loginResponseReceived(Response response);
}

public class LoginActivity extends AppCompatActivity implements ResponseListener{

    public static final String API_LOGIN_URL = "http://cnvcrew.ddns.net/api/";
    LoginHandler handler = new LoginHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler.addListener(this);
        Log.e("Activitty set","");
    }

    public void loginToJson(View v) throws Exception{
        Gson gson = new Gson();
        UtenteLogin login = new UtenteLogin();
        String user = ((EditText) findViewById(R.id.editUsername)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        login.setUsername(user);
        login.setPassword(pass);
        String jsonString = gson.toJson(login);
        Log.i("json", jsonString);
        LoginHandler handler = new LoginHandler();
        handler.addListener(this);

        handler.connect(jsonString);
    }

    public void loginResponseReceived(Response response){
        try{
            Log.i("Received response", response.body().string());
        }catch(Exception e){}
    }
}

class LoginHandler extends AsyncTask<String, String, String> {

    private static final String API_BASE_URL="http://cnvcrew.ddns.net/api/";                        /* Punto di partenza per tutti gli url */
    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        com.squareup.okhttp.OkHttpClient http = new com.squareup.okhttp.OkHttpClient();
        RequestBody form = new FormEncodingBuilder()
                .add("login", params[0])
                .build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url("http://cnvcrew.ddns.net/api/login.php")
                .post(form)
                .build();
        try {
            Log.i("request body", form.toString());
            Log.i("listener reference",listener.toString());
            response = http.newCall(request).execute();
            listener.loginResponseReceived(response);
            Log.i("response",response.toString());
            Log.i("response body",response.body().string());
        }catch(IOException e){
            e.printStackTrace();
        }
        return response.toString();
    }

    public void connect(String data){
        this.execute(data);
    }

    void addListener(ResponseListener listenerToAdd){
        listener=listenerToAdd;
    }

}
