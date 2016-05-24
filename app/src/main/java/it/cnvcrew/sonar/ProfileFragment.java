package it.cnvcrew.sonar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPrefs;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilo, container, false);
        //Get SharedPreferences
        sharedPrefs = getActivity().getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        //Set name and surname TextView with user's name and surname
        //TODO cambiare porcamadonna
        ((TextView) v.findViewById(R.id.tvNome)).setText(MyNavigationDrawer.name);
        ((TextView) v.findViewById(R.id.tvCognome)).setText(MyNavigationDrawer.surname);

        //Blur background image of the header
        BlurImage blurImage = new BlurImage();
        /*ImageView img = (ImageView) v.findViewById(R.id.profile_img_blur);
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.cnv);
        Bitmap blurredImage = blurImage.blur(getContext(), imageToBlur);
        img.setImageBitmap(blurredImage);*/

        return v;
    }

}
