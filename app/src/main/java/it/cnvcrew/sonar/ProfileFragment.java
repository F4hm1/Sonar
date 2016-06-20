package it.cnvcrew.sonar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


public class ProfileFragment extends Fragment implements ResponseListener{

    private SharedPreferences sharedPrefs;
    private String name,surname;
    private Interest[] interests;
    private View view_layout;
    private Gson gson = new Gson();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilo, container, false);
        //Get SharedPreferences
        sharedPrefs = getActivity().getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //((ImageView) v.findViewById(R.id.profile_img)).setImageBitmap(MyNavigationDrawer.loggedUser.getProfile());

        ((ImageView) v.findViewById(R.id.profile_img)).setImageBitmap(MyNavigationDrawer.profilePic);

        ((TextView) v.findViewById(R.id.tvNome)).setText(name);
        ((TextView) v.findViewById(R.id.tvCognome)).setText(surname);

        //Blur background image of the header
        BlurImage blurImage = new BlurImage();
        ImageView img = (ImageView) v.findViewById(R.id.profile_img_blur);
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.mountains);
        Bitmap blurredImage = blurImage.blur(getContext(), imageToBlur);
        img.setImageBitmap(blurredImage);

        GetInterestProfileHandler handler = new GetInterestProfileHandler();
        handler.addListener(this);
        handler.connect(gson.toJson(MyNavigationDrawer.loggedUser));

        view_layout = v;
        return v;
    }

    @Override
    public void onApiResponseReceived(Response response) {
        final Category[] categories;
        try {
            String risposta = response.body().string();
            Log.i("Interest response",risposta);
            JSONArray array = new JSONArray(risposta);
            JSONObject object = array.getJSONObject(array.length() - 1);
            Log.i("operation",object.getString("operation"));
            if(object.getString("operation").equals("get")) {

                Log.i("Interest","getting");
                interests = gson.fromJson(risposta, Interest[].class);
                int nCategories = 0;
                try {
                    JSONArray categoryArray = new JSONArray(risposta);
                    JSONObject userObject = categoryArray.getJSONObject(categoryArray.length() - 2);
                    nCategories = userObject.getInt("numero");
                    Log.i("numero", String.valueOf(nCategories));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (nCategories == 0) {
                    //TODO cose
                }
                categories = new Category[nCategories];
                for(int i = 0; i < nCategories; i++) {
                    categories[i] = new Category(i + 1);
                }

                for(int i = 0; i < interests.length; i++){

                    for(int j = 0; j < nCategories; j++){
                        if(interests[i].getCategory_id() == categories[j].getId()){
                            categories[j].setName(interests[i].getCategory_name());
                            categories[j].setInterests(interests[i]);
                        }
                    }
                }

                final RecyclerView recyclerView = (RecyclerView) view_layout.findViewById(R.id.rv_profile_interests);
                final CategoryProfileRecyclerViewAdapter adapter = new CategoryProfileRecyclerViewAdapter(categories, this.getActivity().getApplicationContext());
                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());

                int interestNumber[] = new int[adapter.getItemCount()];

                this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
            else{
                Log.i("Interests","setting");
                for(int i = 0; i < interests.length; i++) {
                    interests[i].setHas_changed(false);
                }
            }
        }catch(Exception e){
            Log.e("Interest response error",e.toString());
        }
    }
}

class GetInterestProfileHandler extends AsyncTask<String, String, String> {

    Response response = null;
    ResponseListener listener;

    @Override
    protected String doInBackground(String... params){
        String ritorno;
        Log.i("Request",params[0]);
        OkHttpClient http = new OkHttpClient();
        http.setConnectTimeout(5, TimeUnit.SECONDS);
        RequestBody form = new FormEncodingBuilder()
                .add("user", params[0])
                .build();
        Request request = new Request.Builder()
                .url(Resources.API_GET_INTERESTS)
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