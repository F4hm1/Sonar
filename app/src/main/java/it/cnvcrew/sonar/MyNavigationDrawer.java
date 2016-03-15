package it.cnvcrew.sonar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Gianmarco on 05/03/2016.
 */

public class MyNavigationDrawer extends MaterialNavigationDrawer {
    private ProfiloFragment profiloFragment;
    private SettingsActivity settingsActivity;
    @Override
    public void init(Bundle savedInstanceState) {
        profiloFragment = new ProfiloFragment();
        settingsActivity = new SettingsActivity();

        //Functionalities
        this.setDefaultSectionLoaded(0);
        this.allowArrowAnimation();
        this.disableLearningPattern();
        this.enableToolbarElevation();

        /*Sections*/
        //Account
        MaterialAccount account = new MaterialAccount(this.getResources(),"Name Surname","email@example.com",R.drawable.photo, R.drawable.background);
        //Items
        MaterialSection accountSection = newSection("Profilo", R.drawable.ic_account, profiloFragment);
        MaterialSection geoSection = newSection("Posizione", R.drawable.ic_position, new Fragment());
        MaterialSection eventSection = newSection("Eventi", R.drawable.ic_events, new Fragment());
        MaterialSection settingSection = newSection("Impostazioni", R.drawable.ic_settings, new Fragment());
        MaterialSection infoSection = newSection("Info", R.drawable.ic_info, settingsActivity);
        //Add section to nav drawer
        this.addAccount(account);
        this.addSection(accountSection);
        this.addSection(geoSection);
        this.addSection(eventSection);
        this.addDivisor();
        this.addBottomSection(settingSection);
        this.addBottomSection(infoSection);
    }
}