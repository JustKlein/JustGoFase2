package com.example.justgo.CadastroRota;

/**
 * Created by Keven on 8/26/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.justgo.R;

import java.util.ArrayList;


public class PontoItemAdapter extends ArrayAdapter<PontoItem> {
    private boolean mSelectedItem;

    private static final String LOG_TAG = PontoItemAdapter.class.getSimpleName();

    public PontoItemAdapter(Activity context, ArrayList<PontoItem> android) {
        super(context, 0, android);
    }

    public boolean getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem() {
        this.mSelectedItem = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.eu_sou_louca, parent, false);
        }

        PontoItem currentAndroid = getItem(position);

        TextView tv_3 = (TextView) listItemView.findViewById(R.id.n3);
        tv_3.setText(currentAndroid.getOrigem());
        TextView tv_4 = (TextView) listItemView.findViewById(R.id.n4);
        tv_4.setText(currentAndroid.getDestino());

        RotaSingleton rotaSingleton = RotaSingleton.getInstancia();
        ArrayList<Integer> positions = rotaSingleton.positions;

        for(int i = 0; i<positions.size();i++){
            if(position == positions.get(i)){
                tv_3.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
                tv_4.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
            }
        }

        return listItemView;
    }
}