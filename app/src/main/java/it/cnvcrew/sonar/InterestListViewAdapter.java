package it.cnvcrew.sonar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by electrocamel on 09/06/16.
 */
public class InterestListViewAdapter  extends ArrayAdapter<Interest>{

    private final Context context;
    private final Interest[] interests;

    public InterestListViewAdapter(Context context, Interest[] interests){
        super(context, -1 , interests);
        this.context=context;
        this.interests=interests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.interest_listview_element, parent, false);          /* Imposta il layout di un singolo elemento */
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_interest_name);
        //Switch sw_isChecked = (Switch) convertView.findViewById(R.id.sw_is_checked);
        CheckBox cb_isSelected = (CheckBox) convertView.findViewById(R.id.cb_is_selected);
        cb_isSelected.setOnCheckedChangeListener(interests[position]);
        tv_name.setText(interests[position].getName());
        //sw_isChecked.setChecked(interests[position].is_selected());
        cb_isSelected.setChecked(interests[position].is_selected());
        interests[position].setHas_changed(false);
        return convertView;
    }

    @Override
    public int getCount(){
        Log.i("interest count",String.valueOf(interests.length));
        return interests.length;
    }

    public Interest getInterest(int position){
        return interests[position];
    }

}
