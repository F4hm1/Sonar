package it.cnvcrew.sonar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

interface ResponseListener {
    void onLoginResponseReceived(Response response);
}

public class LoginActivity extends AppCompatActivity implements ResponseListener{

    LoginHandler handler = new LoginHandler();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
            Log.i("Received response", responseString);
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
        OkHttpClient http = new OkHttpClient();
        RequestBody form = new FormEncodingBuilder()
                .add("login", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_LOGIN_URL)
                .post(form)
                .build();
        try {
            Log.i("listener reference",listener.toString());
            response = http.newCall(request).execute();
            listener.onLoginResponseReceived(response);
            Log.i("response",response.toString());
//            Log.i("response body",response.body().string());
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
