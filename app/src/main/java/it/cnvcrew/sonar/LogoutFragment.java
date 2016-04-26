package it.cnvcrew.sonar;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());

        //Getting shared preferences
        sharedPrefs = getActivity().getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //Clearing all shared preferences and commit
        sharedPrefsEditor = (sharedPrefs.edit()).clear();
        sharedPrefsEditor.commit();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
        return textView;
    }

}
