package it.cnvcrew.sonar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class LoginActivity extends AppCompatActivity{

    public static final String API_LOGIN_URL = "http://cnvcrew.ddns.net/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginToJson(View v) throws Exception {
        Gson gson = new Gson();
        UtenteLogin login = new UtenteLogin();
        String user = ((EditText) findViewById(R.id.editUsername)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        login.setUsername(user);
        login.setPassword(pass);
        String jsonString = gson.toJson(login);
        Log.i("json", jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        Log.i("jsonObject", jsonObject.toString(4));
        final JSONObject jsonBody = new JSONObject("{\"username\":\"lapse\",\"password\":\"lapse\"}");
        HashMap<String, String> tetta = new HashMap<String, String>();
        tetta.put("username", "lapse");
        tetta.put("password", "lapse");
        HttpHandler handler = new HttpHandler();
        String tettarella;
        handler.connect("login.php",jsonString);
    }
}