package com.example.justgo.Drawer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.justgo.CadastroRota.PontoItem;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.logger.Log;

import java.util.ArrayList;

/**
 * Created by Keven on 9/8/2017.
 */

public class TrechoItemAdapter extends ArrayAdapter<TrechoItem> {
    private static final String LOG_TAG = TrechoItemAdapter.class.getSimpleName();
    Conversor conversor;
    public TrechoItemAdapter(Activity context, ArrayList<TrechoItem> android) {
        super(context, 0, android);
        conversor = new Conversor(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.ponto_item, parent, false);
        }

        TrechoItem currentAndroid = getItem(position);

        TextView descricao = (TextView) listItemView.findViewById(R.id.descricao);
        descricao.setText(currentAndroid.getDescricao());
        android.util.Log.v("Descricao",currentAndroid.getDescricao());
        TextView origem = (TextView) listItemView.findViewById(R.id.origem2);
        origem.setText(currentAndroid.getOrigem());
        android.util.Log.v("Descricao",currentAndroid.getDestino());
        TextView destino = (TextView) listItemView.findViewById(R.id.destino2);
        destino.setText(currentAndroid.getDestino());

        TextView tempo = (TextView) listItemView.findViewById(R.id.tempo);
        tempo.setText(Double.toString(currentAndroid.getTempo()));

        TextView preco = (TextView) listItemView.findViewById(R.id.preco);
        preco.setText(Double.toString(currentAndroid.getPreco()));

        TextView meiodeTransporte = (TextView) listItemView.findViewById(R.id.meioDeTransporte);
        meiodeTransporte.setText(currentAndroid.getMeiodeTransporte());

        return listItemView;
    }
}
