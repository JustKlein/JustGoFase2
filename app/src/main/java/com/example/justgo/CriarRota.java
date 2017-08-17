package com.example.justgo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.justgo.Mapa.LocationIntentService;
import com.example.justgo.Mapa.MessageEB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

public class CriarRota extends AppCompatActivity {
    public static final String ADDRESS = "address";
    private EditText origem;
    private EditText destino;
    private static Double latitudeOrigem,longitudeOrigem,latitudeDestino,longitudeDestino;
    private static Double x,y;
    public eudesisto e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_rota);
        EventBus.getDefault().register(this);
        origem = (EditText) findViewById(R.id.origem);
        destino = (EditText) findViewById(R.id.destino);
        e = new eudesisto();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = new ArrayList<Address>();
String error="";
        String resultAddress="";
        String resultAddress2="";
        try {
            list = (ArrayList<Address>) geocoder.getFromLocationName("Rua Vergara 755", 1);
        } catch (IOException e) {
            e.printStackTrace();
            error = "Network problem";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            error = "Illegal arguments";
        }
        if (list != null && list.size() > 0) {
            Address a = list.get(0);
            resultAddress += a.getLatitude();
            resultAddress2 += a.getLongitude();
        } else {
            resultAddress = error;
        }
        Log.v("ME AJUDA JESUS", resultAddress);


    }
    public void pesquisarDestino(View v) {
        int type;
        String address = null;
        address = origem.getText().toString();
        callIntentService(address);
//                    Log.v("aaaaaaaaaaaaaa",x.toString());
        latitudeOrigem = x;
        longitudeOrigem=y;
        address = destino.getText().toString();
        callIntentService(address);
        Log.v("AAAAAAAAAAAAAAAAA",e.getLatitude().toString());
        latitudeDestino = x;
        longitudeDestino=y;
        Log.v("LATITUDE DE 1", latitudeOrigem.toString());
        //   Log.v("LATITUDE DE 2", latitudeDestino.toString());
    }
    public void callIntentService( String address) {
        Intent it = new Intent(this, LocationIntentService.class);
        it.putExtra(ADDRESS, address);
        startService(it);
    }
    public void onEvent(final MessageEB m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                x = m.getResultLatitude();
                y = m.getResultLongitude();
            }
        });
    }

}
