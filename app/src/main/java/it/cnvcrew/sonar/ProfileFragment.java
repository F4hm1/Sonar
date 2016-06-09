package it.cnvcrew.sonar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


<<<<<<< HEAD:app/src/main/java/it/cnvcrew/sonar/ProfiloFragment.java
public class ProfiloFragment extends Fragment {
=======
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfileFragment extends Fragment {
>>>>>>> dev-alpha:app/src/main/java/it/cnvcrew/sonar/ProfileFragment.java

    private SharedPreferences sharedPrefs;
    private String name, surname;

    public ProfiloFragment(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public ProfiloFragment(){

<<<<<<< HEAD:app/src/main/java/it/cnvcrew/sonar/ProfiloFragment.java
=======
    public ProfileFragment() {
        // Required empty public constructor
>>>>>>> dev-alpha:app/src/main/java/it/cnvcrew/sonar/ProfileFragment.java
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilo, container, false);
        //Get SharedPreferences
        sharedPrefs = getActivity().getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //((ImageView) v.findViewById(R.id.profile_img)).setImageBitmap(MyNavigationDrawer.loggedUser.getProfile());

        ((ImageView) v.findViewById(R.id.profile_img)).setImageBitmap(MyNavigationDrawer.profilePic);

        //Set name and surname TextView with user's name and surname
        ((TextView) v.findViewById(R.id.tvNome)).setText(name);
        ((TextView) v.findViewById(R.id.tvCognome)).setText(surname);

        //Blur background image of the header
        BlurImage blurImage = new BlurImage();
        /*ImageView img = (ImageView) v.findViewById(R.id.profile_img_blur);
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.cnv);
        Bitmap blurredImage = blurImage.blur(getContext(), imageToBlur);
        img.setImageBitmap(blurredImage);*/

        return v;
    }

}
