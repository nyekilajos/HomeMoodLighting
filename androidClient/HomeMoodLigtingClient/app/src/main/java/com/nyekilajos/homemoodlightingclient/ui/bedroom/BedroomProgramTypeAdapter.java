package com.nyekilajos.homemoodlightingclient.ui.bedroom;

import java.util.Arrays;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.nyekilajos.homemoodlightingclient.R;
import com.nyekilajos.homemoodlightingclient.persitency.BedroomProgram;

/**
 * Adapter for displaying bedroom program types with a spinner.
 * 
 * @author Lajos_Nyeki
 */
public class BedroomProgramTypeAdapter extends BaseAdapter {

    private final Context context;

    @Inject
    public BedroomProgramTypeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return BedroomProgram.BedroomProgramType.values().length;
    }

    @Override
    public Object getItem(int position) {
        return BedroomProgram.BedroomProgramType.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BedroomProgram.BedroomProgramType bedroomProgramType = BedroomProgram.BedroomProgramType.values()[position];

        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_bedroom_program_type_spinner, parent, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.bedroom_program_type_spinner_icon);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, bedroomProgramType.getIconResId()));

        TextView textView = (TextView) view.findViewById(R.id.bedroom_program_type_spinner_text);
        textView.setText(context.getString(bedroomProgramType.getNameResId()));

        return view;
    }

    public int getPosition(BedroomProgram.BedroomProgramType bedroomProgramType) {
        return Arrays.binarySearch(BedroomProgram.BedroomProgramType.values(), bedroomProgramType);
    }
}
