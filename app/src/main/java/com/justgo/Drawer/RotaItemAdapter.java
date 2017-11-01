package com.justgo.Drawer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.justgo.CadastroRota.PontoItemAdapter;
import com.justgo.R;

import java.util.ArrayList;

/**
 * Created by Keven on 9/5/2017.
 */

public class RotaItemAdapter extends ArrayAdapter<RotaItem> {
    private static final String LOG_TAG = PontoItemAdapter.class.getSimpleName();

    public RotaItemAdapter(Activity context, ArrayList<RotaItem> android) {
        super(context, 0, android);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.rota_item, parent, false);
        }

        RotaItem currentAndroid = getItem(position);

        TextView nome = (TextView) listItemView.findViewById(R.id.nome);
        nome.setText(currentAndroid.getNomeRota());

        //TextView tv_Rmanutencao = (TextView) listItemView.findViewById(R.id.n1);
        //tv_Rmanutencao.setText(currentAndroid.getOrigem());

       // TextView t2 = (TextView) listItemView.findViewById(R.id.n2);
        //t2.setText(currentAndroid.getDestino());
        return listItemView;
    }
}
