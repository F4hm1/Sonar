package it.cnvcrew.sonar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {

    private Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PrefetchLogin login = new PrefetchLogin();
        login.setActivity(this);
        login.run();

    }



    private class PrefetchLogin extends AsyncTask<Void, Void, String> {

        private Activity activity;

        @Override
        protected String doInBackground(Void... args) {
            Log.i("SplashScreen","Started");
            SharedPreferences prefs = getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Response response = null;
            if (prefs.contains("username") && prefs.contains("password")) {
                Log.i("SplashScreen","SharedPrefs found!");
                UtenteLogin utenteLogin = new UtenteLogin(prefs.getString("username", null), prefs.getString("password", null));
                OkHttpClient http = new OkHttpClient();
                http.setConnectTimeout(5, TimeUnit.SECONDS);
                RequestBody form = new FormEncodingBuilder()
                        .add("login", gson.toJson(utenteLogin))
                        .build();
                Request request = new Request.Builder()
                        .url(Resources.API_LOGIN_URL)
                        .post(form)
                        .build();
                try {
                    response = http.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    Intent loginIntent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(loginIntent);
                    this.activity.finish();
                    Log.i("Result", "ko");
                }
                try {
                    String responseString = response.body().string();
                    Log.i("Received user JSON",responseString);
                    User user = gson.fromJson(responseString, User.class);
                    Log.i("Received user", user.toString());
                    //File imgProFile = new File(this.getFilesDir(), "profilePic");
                    FileOutputStream outputStream = openFileOutput("profilePic", Context.MODE_PRIVATE);
                    outputStream.write(user.getProfileBase64().getBytes());
                    outputStream.close();
                    user.setProfileBase64("file");
                    if (user.getId() != -1) {

                        Intent mainActivityIntent = new Intent(activity, MyNavigationDrawer.class);
                        mainActivityIntent.putExtra("userJson", gson.toJson(user));
                        activity.startActivity(mainActivityIntent);
                        this.activity.finish();
                    } else {
                        Intent loginIntent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(loginIntent);
                        this.activity.finish();
                        Log.i("Result", "ko");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent loginIntent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(loginIntent);
                    this.activity.finish();
                    Log.i("Result", "ko");
                }

            }else{
                Log.w("SplashScreen","No SharedPrefs found");
                Intent loginIntent = new Intent(activity, LoginActivity.class);
                activity.startActivity(loginIntent);
                this.activity.finish();
                Log.i("Result", "ko");
            }
            return null;
        }

        private void setActivity(Activity activity){
            this.activity = activity;
        }

        public void run(){
            this.execute();
        }
    }
}
