package it.cnvcrew.sonar;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

public class SettingsActivity extends PreferenceActivity
{
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.headers_settings, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return SettingsActivity.class.getName().equals(fragmentName);
    }
}