package com.example.justgo.CadastroRota;

/**
 * Created by Keven on 8/26/2017.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.justgo.R;

import java.util.ArrayList;


public class PontoItemAdapter extends ArrayAdapter<PontoItem> {

    private static final String LOG_TAG = PontoItemAdapter.class.getSimpleName();

    public PontoItemAdapter(Activity context, ArrayList<PontoItem> android) {
        super(context, 0, android);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.eu_sou_louca, parent, false);
        }

        PontoItem currentAndroid = getItem(position);

        TextView tv_Rmanutencao = (TextView) listItemView.findViewById(R.id.n1);
        tv_Rmanutencao.setText(Integer.toString(currentAndroid.getCodPonto()));
        TextView tv_Rdata = (TextView) listItemView.findViewById(R.id.n2);
        tv_Rdata.setText(Integer.toString(currentAndroid.getCodRota()));
        TextView tv_3 = (TextView) listItemView.findViewById(R.id.n3);
        tv_3.setText(currentAndroid.getOrigem());
        TextView tv_4 = (TextView) listItemView.findViewById(R.id.n4);
        tv_4.setText(currentAndroid.getDestino());

        return listItemView;
    }
}