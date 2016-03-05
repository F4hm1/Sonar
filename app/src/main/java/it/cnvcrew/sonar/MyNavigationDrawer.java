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

    @Override
    public void init(Bundle savedInstanceState) {
        //Functionalities
        this.setDefaultSectionLoaded(0);
        this.allowArrowAnimation();
        this.disableLearningPattern();
        this.enableToolbarElevation();

        /*Sections*/
        //Account
        MaterialAccount account = new MaterialAccount(this.getResources(),"Name Surname","email@example.com",R.drawable.photo, R.drawable.background);
        //Items
        MaterialSection accountSection = newSection("Profilo", new Fragment());
        MaterialSection geoSection = newSection("Posizione", new Fragment());
        MaterialSection eventSection = newSection("Eventi", new Fragment());
        MaterialSection settingSection = newSection("Impostazioni", new Fragment());
        MaterialSection infoSection = newSection("Info", new Fragment());
        //Add section to nav drawer
        this.addAccount(account);
        this.addAccountSection(accountSection);
        this.addAccountSection(geoSection);
        this.addAccountSection(eventSection);
        this.addDivisor();
        this.addBottomSection(settingSection);
        this.addBottomSection(infoSection);
    }
}