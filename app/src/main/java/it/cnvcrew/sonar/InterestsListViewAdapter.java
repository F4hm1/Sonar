package it.cnvcrew.sonar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Alessandro on 14/05/2016.
 */
public class InterestsListViewAdapter extends ArrayAdapter<Interest> {

    private final Context context;
    private final Interest[] interests;

    public InterestsListViewAdapter(Context context, int resource, Interest[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.interests=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.interest_listview_element, parent,false);

        return convertView;
    }
}
