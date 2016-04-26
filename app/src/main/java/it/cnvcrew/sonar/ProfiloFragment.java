package it.cnvcrew.sonar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfiloFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfiloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfiloFragment extends Fragment {

    private SharedPreferences sharedPrefs;

    public ProfiloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilo, container, false);

        //Get SharedPreferences
        sharedPrefs = getActivity().getSharedPreferences(Resources.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        //Set name and surname TextView with user's name and surname
        /*((TextView) v.findViewById(R.id.tvNome)).setText("");
        ((TextView) v.findViewById(R.id.tvCognome)).setText("");*/

        //Blur background image of the header
        BlurImage blurImage = new BlurImage();
        ImageView img = (ImageView) v.findViewById(R.id.profile_img_blur);
        Bitmap imageToBlur = BitmapFactory.decodeResource(getResources(), R.drawable.cnv);
        Bitmap blurredImage = blurImage.blur(getContext(), imageToBlur);
        img.setImageBitmap(blurredImage);
        return v;
    }

}
