package it.cnvcrew.sonar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.gson.Gson;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Gianmarco on 05/03/2016.
 */

public class MyNavigationDrawer extends MaterialNavigationDrawer {
    Gson gson = new Gson();

    private ProfiloFragment profiloFragment;
    private SettingsFragment settingsFragment;
    private EventsFragment eventsFragment;
    private LogoutFragment logoutFragment;

    @Override
    public void init(Bundle savedInstanceState) {
        Intent receivedIntent = getIntent();
        String loginResponse = receivedIntent.getStringExtra("userJson");
        User user = gson.fromJson(loginResponse,User.class);
        String name = user.getNome();
        String surname = user.getCognome();
        String email = user.getEmail();

        profiloFragment = new ProfiloFragment();
        eventsFragment = new EventsFragment();
        settingsFragment = new SettingsFragment();
        logoutFragment = new LogoutFragment();

        //Functionalities
        this.setDefaultSectionLoaded(0);
        this.allowArrowAnimation();
        this.disableLearningPattern();
        //this.enableToolbarElevation();

        //Blur header background image
        BlurImage blurImage = new BlurImage();
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        Bitmap blurredImage = blurImage.blur(getApplicationContext(), imageToBlur);

        /*Sections*/
        //Account
        MaterialAccount account = new MaterialAccount(this.getResources(),name + " " + surname,email,R.drawable.cnv, blurredImage);
        //Items
        MaterialSection accountSection = newSection("Profilo", R.drawable.ic_account, profiloFragment);
        MaterialSection geoSection = newSection("Posizione", R.drawable.ic_position, new Fragment());
        MaterialSection eventSection = newSection("Eventi", R.drawable.ic_events, eventsFragment);
        MaterialSection settingSection = newSection("Impostazioni", R.drawable.ic_settings, settingsFragment);
        MaterialSection infoSection = newSection("Info", R.drawable.ic_info, new Fragment());
        MaterialSection logoutSection = newSection("Logout", logoutFragment);
        //Add section to nav drawer
        this.addAccount(account);
        this.addSection(accountSection);
        this.addSection(geoSection);
        this.addSection(eventSection);
        this.addSubheader("Altro");
        this.addSection(settingSection);
        this.addSection(infoSection);
        this.addBottomSection(logoutSection);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(getCurrentSection().getTitle().equals("Eventi")) {
            getMenuInflater().inflate(R.menu.menu_fragment_eventi, menu);
        }
        return true;
    }

    public void newEvent(MenuItem menuItem){
        Intent i = new Intent(this, CreateNewEventActivity.class);
        startActivity(i);
    }

}