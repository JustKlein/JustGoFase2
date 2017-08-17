package com.example.justgo;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Mapa.GetPontoRequest;
import com.example.justgo.Mapa.HomePageMaps;
import com.example.justgo.Mapa.OrigemDestinoAux;
import com.example.justgo.Mapa.OrigemDestinoMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CriarRota extends FragmentActivity{
    private EditText origem, destino;
    private Conversor c;
    private Double[] arrayOrigem;
    private Double[] arrayDestino;
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
    public static OrigemDestinoAux origemDestino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_rota);
        origem = (EditText) findViewById(R.id.origem);
        destino = (EditText) findViewById(R.id.destino);
        c = new Conversor(getApplicationContext());
        arrayOrigem = new Double[2];
        arrayDestino = new Double[2];
      /*  MapaFragment map = new MapaFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag,map,"frag1");
        ft.commit();*/
    }
    public void BuscarOrigemDestino(View v) {
        String addressOrigem = origem.getText().toString();
        String addressDestino = destino.getText().toString();
        arrayOrigem = c.addressToLatLng(addressOrigem);
        Log.v("LATITUDE",arrayOrigem[0].toString());
        Log.v("LONGITUDE",arrayOrigem[1].toString());
        arrayDestino = c.addressToLatLng(addressDestino);
        Log.v("LATITUDE",arrayDestino[0].toString());
        Log.v("LONGITUDE",arrayDestino[1].toString());

        if(arrayDestino != null && arrayOrigem!=null){
            origemDestino = new OrigemDestinoAux();
            origemDestino.setOrigemLat(arrayOrigem[0]);
            origemDestino.setOrigemLng(arrayOrigem[1]);
            origemDestino.setDestinoLat(arrayDestino[0]);
            origemDestino.setDestinoLng(arrayDestino[1]);
            Intent intent = new Intent(this,OrigemDestinoMaps.class);
            startActivity(intent);
        }
    }
    public void ConfirmarOrigemDestino(View v) {
        String addressOrigem = origem.getText().toString();
        String addressDestino = destino.getText().toString();
        arrayOrigem = c.addressToLatLng(addressOrigem);
        Log.v("LATITUDE",arrayOrigem[0].toString());
        Log.v("LONGITUDE",arrayOrigem[1].toString());
        arrayDestino = c.addressToLatLng(addressDestino);
        Log.v("LATITUDE",arrayDestino[0].toString());
        Log.v("LONGITUDE",arrayDestino[1].toString());

        if(arrayDestino != null && arrayOrigem!=null){
            origemDestino = new OrigemDestinoAux();
            origemDestino.setOrigemLat(arrayOrigem[0]);
            origemDestino.setOrigemLng(arrayOrigem[1]);
            origemDestino.setDestinoLat(arrayDestino[0]);
            origemDestino.setDestinoLng(arrayDestino[1]);
            Intent intent = new Intent(this,AdicionarPontosaRota.class);
            startActivity(intent);
        }
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        LatLng sydney = new LatLng(arrayOrigem[0], arrayOrigem[1]);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("MARKER"));
//
//        CameraPosition cameraPosition = CameraPosition.builder()
//                .target(sydney)
//                .zoom(10)
//                .build();
//
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }
}

//package com.example.justgo;
//
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.example.justgo.Mapa.LocationIntentService;
//import com.example.justgo.Mapa.MessageEB;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import de.greenrobot.event.EventBus;
//
//public class CriarRota extends AppCompatActivity {
//    public static final String ADDRESS = "address";
//    private EditText origem;
//    private EditText destino;
//    private static Double latitudeOrigem,longitudeOrigem,latitudeDestino,longitudeDestino;
//    private static Double x,y;
//    public eudesisto e;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_criar_rota);
//        EventBus.getDefault().register(this);
//        origem = (EditText) findViewById(R.id.origem);
//        destino = (EditText) findViewById(R.id.destino);
//        e = new eudesisto();
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> list = new ArrayList<Address>();
//String error="";
//        String resultAddress="";
//        String resultAddress2="";
//        try {
//            list = (ArrayList<Address>) geocoder.getFromLocationName("Rua Vergara 755", 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            error = "Network problem";
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            error = "Illegal arguments";
//        }
//        if (list != null && list.size() > 0) {
//            Address a = list.get(0);
//            resultAddress += a.getLatitude();
//            resultAddress2 += a.getLongitude();
//        } else {
//            resultAddress = error;
//        }
//        Log.v("ME AJUDA JESUS", resultAddress);
//
//
//    }
//    public void pesquisarDestino(View v) {
//        int type;
//        String address = null;
//        address = origem.getText().toString();
//        callIntentService(address);
////                    Log.v("aaaaaaaaaaaaaa",x.toString());
//        latitudeOrigem = x;
//        longitudeOrigem=y;
//        address = destino.getText().toString();
//        callIntentService(address);
//        Log.v("AAAAAAAAAAAAAAAAA",e.getLatitude().toString());
//        latitudeDestino = x;
//        longitudeDestino=y;
//        Log.v("LATITUDE DE 1", latitudeOrigem.toString());
//        //   Log.v("LATITUDE DE 2", latitudeDestino.toString());
//    }
//    public void callIntentService( String address) {
//        Intent it = new Intent(this, LocationIntentService.class);
//        it.putExtra(ADDRESS, address);
//        startService(it);
//    }
//    public void onEvent(final MessageEB m) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                x = m.getResultLatitude();
//                y = m.getResultLongitude();
//            }
//        });
//    }
//
//}
