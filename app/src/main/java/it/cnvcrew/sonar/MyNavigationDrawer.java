package it.cnvcrew.sonar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

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

        //Functionalities
        this.setDefaultSectionLoaded(0);
        this.allowArrowAnimation();
        this.disableLearningPattern();
        //this.enableToolbarElevation();

        /*Sections*/
        //Account
        MaterialAccount account = new MaterialAccount(this.getResources(),name + " " + surname,email,R.drawable.photo, R.drawable.background);
        //Items
        MaterialSection accountSection = newSection("Profilo", R.drawable.ic_account, profiloFragment);
        MaterialSection geoSection = newSection("Posizione", R.drawable.ic_position, new Fragment());
        MaterialSection eventSection = newSection("Eventi", R.drawable.ic_events, eventsFragment);
        MaterialSection settingSection = newSection("Impostazioni", R.drawable.ic_settings, settingsFragment);
        MaterialSection infoSection = newSection("Info", R.drawable.ic_info, new Fragment());
        //Add section to nav drawer
        this.addAccount(account);
        this.addSection(accountSection);
        this.addSection(geoSection);
        this.addSection(eventSection);
        this.addSubheader("Altro");
        this.addSection(settingSection);
        this.addSection(infoSection);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(getCurrentSection().getTitle().equals("Profilo")) {

        }
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