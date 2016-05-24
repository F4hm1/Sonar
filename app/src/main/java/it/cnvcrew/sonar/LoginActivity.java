package it.cnvcrew.sonar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements ResponseListener{

    LoginHandler handler = new LoginHandler();
    Gson gson = new Gson();
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor sharedPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefs = getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        handler.addListener(this);
        Log.e("Activity set","");

        //Set the link for "Registrati" underline
        String udata="Registrati";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        ((TextView) findViewById(R.id.tv_register)).setText(content);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, R.id.bSubmit);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "invia");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }

    public void loginToJson(View v) throws Exception{
        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        UtenteLogin login = new UtenteLogin();
        LoginHandler handler = new LoginHandler();
        handler.addListener(this);

        String user = ((EditText) findViewById(R.id.edit_username_login)).getText().toString();
        String pass = ((EditText) findViewById(R.id.edit_password_login)).getText().toString();
        MaterialTextField mtfuser = (MaterialTextField) findViewById(R.id.edit_username_login_layout);
        MaterialTextField mtfpass = (MaterialTextField) findViewById(R.id.edit_password_login_layout);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if(user.length()==0) {
            ((EditText) findViewById(R.id.edit_username_login)).setError("Username vuoto");
            mtfuser.startAnimation(shake);
        }
        if(pass.length()==0) {
            ((EditText) findViewById(R.id.edit_password_login)).setError("Password vuota");
            mtfpass.startAnimation(shake);
        }
        login.setUsername(user);
        login.setPassword(pass);
        sharedPrefsEditor.putString("username",user);
        sharedPrefsEditor.putString("password",pass);
        String jsonString = gson.toJson(login);
        Log.i("json", jsonString);

        if(sharedPrefs.contains("login")){
            String userPrefs = sharedPrefs.getString("username",null);                              /* Se esiste un utente salvato */
            String passPrefs = sharedPrefs.getString("password",null);
            if(user.equals(userPrefs) && pass.equals(passPrefs)){
                Intent intent = new Intent(this,MyNavigationDrawer.class);
                intent.putExtra("userJson", sharedPrefs.getString("user",null));
                this.startActivity(intent);
                this.finish();
            }else{
                handler.connect(jsonString);
            }
        }
        handler.connect(jsonString);
    }

    public void onApiResponseReceived(Response response){
        try{
            String responseString = response.body().string();
            User user = gson.fromJson(responseString,User.class);
            Log.i("Received JSON",responseString);
            Log.i("Received user", user.toString());
            if(user.getId()!= -1 /*|| user.getId() == null*/) {
                sharedPrefsEditor.apply();
                Log.i("SharedPrefs","Written");
                Intent mainActivityIntent = new Intent(this, MyNavigationDrawer.class);
                mainActivityIntent.putExtra("userJson", gson.toJson(user));
                this.startActivity(mainActivityIntent);
                this.finish();
            }else{
                Snackbar.make(this.findViewById(R.id.bSubmit),
                        "Login Errato.\nAttenzione: il login è case-sensitive!",
                        Snackbar.LENGTH_LONG).show();
                findViewById(R.id.edit_username_login_layout)
                        .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
                findViewById(R.id.edit_password_login_layout)
                        .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
                Log.i("Result","ko");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void launchRegister (View v){
        startActivity(new Intent(this, RegistraActivity.class));
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
