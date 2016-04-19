package it.cnvcrew.sonar;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

interface ResponseListener {
    void onLoginResponseReceived(Response response);
}

public class LoginActivity extends AppCompatActivity implements ResponseListener{

    LoginHandler handler = new LoginHandler();
    public static TextView tetta;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tetta = (TextView) findViewById(R.id.tv_prova);
        handler.addListener(this);
        Log.e("Activity set","");
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

    public void onLoginResponseReceived(Response response){
        try{
            String responseString = response.body().string();
            User user = gson.fromJson(responseString,User.class);
            Log.i("Received user", user.toString());
            if(user.getId()!=-1) {
                Intent mainActivityIntent = new Intent(this, MyNavigationDrawer.class);
                mainActivityIntent.putExtra("userJson", gson.toJson(user));
                this.startActivity(mainActivityIntent);
            }else{
//                Toast errorToast = Toast.makeText(this,"Login errato",Toast.LENGTH_LONG);
                Log.i("Result","ko");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class LoginHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;

        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("login", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_LOGIN_URL)
                .post(form)
                .build();
        try {
            Log.i("listener reference",listener.toString());
            this.publishProgress("richiesta");
            response = http.newCall(request).execute();
            this.publishProgress("risposta");
            listener.onLoginResponseReceived(response);
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
        LoginActivity.tetta.setText(values[0]);
    }

    public void connect(String data){
        this.execute(data);
    }

    void addListener(ResponseListener listenerToAdd){
        listener=listenerToAdd;
    }

}
