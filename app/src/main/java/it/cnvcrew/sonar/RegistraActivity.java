package it.cnvcrew.sonar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class RegistraActivity extends AppCompatActivity implements ResponseListener{

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_giorni);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.giorni_trentuno, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.spinner_mesi);
        adapter = ArrayAdapter.createFromResource(this, R.array.mesi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.spinner_anni);
        adapter = ArrayAdapter.createFromResource(this, R.array.anni, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void loginToJson(View v){

        String username,nome,cognome,email,password,confermapassword;
        int yob,mob,dayob;

        yob = Integer.parseInt(((Spinner) findViewById(R.id.spinner_anni)).getSelectedItem().toString());
        mob = Integer.parseInt(((Spinner) findViewById(R.id.spinner_mesi)).getSelectedItem().toString());
        dayob = Integer.parseInt(((Spinner) findViewById(R.id.spinner_giorni)).getSelectedItem().toString());

        username = ((EditText) findViewById(R.id.editUsername)).getText().toString();
        nome = ((EditText) findViewById(R.id.editName)).getText().toString();
        cognome = ((EditText) findViewById(R.id.editSurname)).getText().toString();
        email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        password = ((EditText) findViewById(R.id.editPsw)).getText().toString();
        confermapassword = ((EditText) findViewById(R.id.editConfPsw)).getText().toString();
        Log.i("password", password);
        Log.i("conferma password", confermapassword);
        Date dob = new Date(yob,mob,dayob);

        User toRegister = new User(nome, cognome, username, email, password, dob);

        if(password.equals(confermapassword)){
            String jsonString = gson.toJson(toRegister);
            Log.i("json", jsonString);
            SignUpHandler handler = new SignUpHandler();
            handler.addListener(this);

            handler.connect(jsonString);
        }
        else{
            Log.e("register","password non collimanti");
        }

    }

    @Override
    public void onApiResponseReceived(Response response) {

    }
}

class SignUpHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;

        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("signup", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_REGISTER_URL)
                .post(form)
                .build();
        try {
            Log.i("listener reference",listener.toString());
            this.publishProgress("richiesta");
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

    public void connect(String data){
        this.execute(data);
    }

    void addListener(ResponseListener listenerToAdd){
        listener=listenerToAdd;
    }

}