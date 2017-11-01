package com.justgo.CadastroRota;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.justgo.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    String[] transportes ={"À pé","Avião","Carro","Metrô","Ônibus","Táxi","Trem","Uber"};
    int [] meioDeTransp = {R.drawable.ic_ape,R.drawable.ic_aviao,R.drawable.ic_carro,R.drawable.ic_metro,
            R.drawable.ic_onibus,R.drawable.ic_taxi,R.drawable.ic_trem,R.drawable.ic_uber};


    public CustomAdapter(Context applicationContext, int[] meioDeTransp, String[] transportes) {
        this.context = applicationContext;
        this.meioDeTransp = meioDeTransp;
        this.transportes = transportes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return meioDeTransp.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView names = (TextView) view.findViewById(R.id.text1);
        icon.setImageResource(meioDeTransp[i]);
        names.setText(transportes[i]);
        return view;
    }
}