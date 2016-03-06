package it.cnvcrew.sonar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginToJson(View v){
        Gson gson = new Gson();
        UtenteLogin login = new UtenteLogin();
        String user = ((EditText) findViewById(R.id.editUsername)).getText().toString();
        String pass = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        login.setUsername(user);
        login.setPassword(pass);
        String json = gson.toJson(login);
        ApiHandler api = new ApiHandler();
        api.toApi("login.php",json);

        Toast.makeText(this, "Utente: "+user+"\nPassword: "+pass, Toast.LENGTH_LONG).show();
    }
}
