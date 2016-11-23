package it.cnvcrew.sonar;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

// TODO mettere errore/shake su campo vuoto come su login
public class RegistraActivity extends AppCompatActivity implements ResponseListener, DatePickerDialog.OnDateSetListener{

    Gson gson = new Gson();
    private int yob,mob,dayob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        /*Spinner spinner = (Spinner) findViewById(R.id.spinner_giorni);
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
        spinner.setAdapter(adapter);*/

    }

    public void showDatePicker(View v){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    public void loginToJson(View v){

        String username,nome,cognome,email,password,confermapassword;
        boolean emptyField = false;

        Log.i("YOB", String.valueOf(yob));
        Log.i("MOB", String.valueOf(mob));
        Log.i("DAYOB", String.valueOf(dayob));

        username = ((EditText) findViewById(R.id.edit_username_register)).getText().toString();
        nome = ((EditText) findViewById(R.id.editName)).getText().toString();
        cognome = ((EditText) findViewById(R.id.editSurname)).getText().toString();
        email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        password = ((EditText) findViewById(R.id.edit_password_register)).getText().toString();
        confermapassword = ((EditText) findViewById(R.id.editConfPsw)).getText().toString();

        if(username.length() == 0){
            findViewById(R.id.edit_username_register_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.edit_username_register)).setError("Campo vuoto");
            emptyField = true;
        }
        if(nome.length() == 0){
            findViewById(R.id.editName_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.editName)).setError("Campo vuoto");
            emptyField = true;
        }
        if(cognome.length() == 0){
            findViewById(R.id.editSurname_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.editSurname)).setError("Campo vuoto");
            emptyField = true;
        }
        if(email.length() == 0){
            findViewById(R.id.editEmail_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.editEmail)).setError("Campo vuoto");
            emptyField = true;
        }
        if(password.length() == 0){
            findViewById(R.id.edit_password_register_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.edit_password_register)).setError("Campo vuoto");
            emptyField = true;
        }

        if(confermapassword.length() == 0){
            findViewById(R.id.editConfPsw_layout)
                    .startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            ((EditText) findViewById(R.id.editConfPsw)).setError("Campo vuoto");
            emptyField = true;
        }

        Log.i("password", password);
        Log.i("conferma password", confermapassword);
        String dob = yob+"-"+mob+"-"+dayob;

        Log.i("DATE",dob);

        if(emptyField == false) {
            User toRegister = new User(nome, cognome, username, email, password, dob);

            if (password.equals(confermapassword)) {
                String jsonString = gson.toJson(toRegister);
                Log.i("json", jsonString);
                SignUpHandler handler = new SignUpHandler();
                handler.addListener(this);

                handler.connect(jsonString);
            } else {
                Log.e("register", "password non collimanti");
            }
        }else{
            Snackbar.make(findViewById(R.id.bSubmit),"E' necessario compilare tutti i campi",Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onApiResponseReceived(Response response){
        String responseMessage = null;
        try {
            responseMessage = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Risposta registra",responseMessage);
        SignupResponse signupResponse = new SignupResponse();
        try {
            signupResponse.setMessage(new JSONObject().getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = signupResponse.getMessage();
        Log.i("message",message);
        if(!message.equals("user_exists")) {
            Snackbar.make(this.findViewById(R.id.iv_logo_register), "Utente registrato con successo.", Snackbar.LENGTH_LONG).show();
            this.finish();
        }
        else{
            Snackbar.make(this.findViewById(R.id.bSubmit), "Utente già esistente!.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 += 1;                                                                                    /* Cose strane di Android */
        yob = i;
        mob = i1;
        dayob = i2;
        ((TextView) findViewById(R.id.tv_dob)).setText(String.valueOf(i2) + "/" + String.valueOf(i1) + "/" + String.valueOf(i));
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
            Log.i("response",response.toString());
            Log.i("response body",response.body().string());
            this.publishProgress("risposta");
            listener.onApiResponseReceived(response);
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
