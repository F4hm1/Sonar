package it.cnvcrew.sonar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Gianmarco on 05/03/2016.
 */

public class MyNavigationDrawer extends MaterialNavigationDrawer {
    Gson gson = new Gson();

    private ProfileFragment profiloFragment;
    private SettingsFragment settingsFragment;
    private EventsFragment eventsFragment;
    private LogoutFragment logoutFragment;
    private PositionFragment positionFragment;
    private AboutFragment aboutFragment;
    public static String name,surname,email;
    protected static Bitmap profilePic;
    protected static User loggedUser;

    @Override
    public void init(Bundle savedInstanceState) {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent receivedIntent = getIntent();
        String loginResponse = receivedIntent.getStringExtra("userJson");
        loggedUser = gson.fromJson(loginResponse, User.class);
        mFirebaseAnalytics.setUserId(String.valueOf(loggedUser.getId()) + loggedUser.getNome() + loggedUser.getCognome());
        name = loggedUser.getNome();
        surname = loggedUser.getCognome();
        email = loggedUser.getEmail();

        profiloFragment = new ProfileFragment(name, surname);
        //ProfileFragment = new ProfileFragment();
        eventsFragment = new EventsFragment();
        settingsFragment = new SettingsFragment();
        logoutFragment = new LogoutFragment();
        positionFragment = new PositionFragment();
        aboutFragment = new AboutFragment();
        
        //Functionalities
        this.setDefaultSectionLoaded(0);
        this.allowArrowAnimation();
        this.disableLearningPattern();
        //this.enableToolbarElevation();

        //Blur header background image
        BlurImage blurImage = new BlurImage();
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account);
        Bitmap blurredImage = blurImage.blur(getApplicationContext(), imageToBlur);

        try{
            InputStream inputStream = openFileInput("profilePic");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String image64 = bufferedReader.readLine();
            byte[] imgBytes = Base64.decode(image64, 0);
            profilePic = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        }catch(Exception e){
            e.printStackTrace();
            Log.e("tettaprofilo", e.toString());
        }
//        Log.w("profile pic",profilePic.toString());

        /*Sections*/
        //Account
        MaterialAccount account = new MaterialAccount(this.getResources(),name + " " + surname,email,profilePic, R.drawable.sunsetswerve);
        //Items
        MaterialSection accountSection = newSection("Profilo", R.drawable.ic_account, profiloFragment);
        MaterialSection geoSection = newSection("Posizione", R.drawable.ic_position, positionFragment);
        MaterialSection eventSection = newSection("Eventi", R.drawable.ic_events, eventsFragment);
        MaterialSection settingSection = newSection("Impostazioni", R.drawable.ic_settings, SettingsActivity.class);
        MaterialSection aboutSection = newSection("Info", R.drawable.ic_info, aboutFragment);
        MaterialSection logoutSection = newSection("Logout", logoutFragment);
        //Add section to nav drawer
        this.addAccount(account);
        this.addSection(accountSection);
        this.addSection(geoSection);
        this.addSection(eventSection);
        this.addSubheader("Altro");
        this.addSection(settingSection);
        this.addSection(aboutSection);
        this.addBottomSection(logoutSection);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(getCurrentSection().getTitle().equals("Eventi")) {
            getMenuInflater().inflate(R.menu.menu_fragment_eventi, menu);
        }
        if(getCurrentSection().getTitle().equals("Profilo")) {
            getMenuInflater().inflate(R.menu.menu_fragment_profilo, menu);
        }
        return true;
    }

    public void newEvent(MenuItem menuItem){
        Intent i = new Intent(this, CreateNewEventActivity.class);
        startActivity(i);
    }

    public void openInterests(MenuItem menuItem){
        Intent i = new Intent(this, InterestsActivity.class);
        startActivity(i);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
