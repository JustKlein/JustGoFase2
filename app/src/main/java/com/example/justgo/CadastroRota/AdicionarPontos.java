package com.example.justgo.CadastroRota;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class AdicionarPontos extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener {
    private EditText etPonto;
    private GoogleMap mMap;
    public static RotaAux rota;
    private Conversor c,c2;
    private Double[] pontoLatLng;
    private ProgressDialog progressDialog;
    Polyline polyline;
    private List<LatLng> pontos;
    private List<Polyline> list;
    private List<Marker> markers;
    Polyline t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pontos);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.AdicionarPontosMap);
        mapFragment.getMapAsync(this);
        etPonto = (EditText) findViewById(R.id.editAddPonto);
        pontoLatLng = new Double[2];
        c = new Conversor(getApplicationContext());
        c2 = new Conversor(getApplicationContext());
        list = new ArrayList<Polyline>();
        markers = new ArrayList<Marker>();
        pontos = new ArrayList<LatLng>();
        c2.latLngtoAddress(-19.8986831,-44.0272054);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng origem = new LatLng(rota.getOrigemLat(), rota.getOrigemLng());
        markers.add(mMap.addMarker(new MarkerOptions().position(origem).title("Origem")/*.draggable(true)*/));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origem,15));
        pontos.add(origem);
        aplicaPolyline();
    }
    public void aplicaPolyline(){
        PolylineOptions rectOptions;
        rectOptions = new PolylineOptions();
        for(int i =0;i<pontos.size();i++){
            rectOptions.add(pontos.get(i)).width(5).color(Color.BLUE);
            //Log.v("as",pontos.get(i).toString());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pontos.get(pontos.size()-1),14));
        list.add(mMap.addPolyline(rectOptions));
    }
    public void botaoRemovePonto(View v){
        Log.v("assaasd",Integer.toString(pontos.size()));
        pontos.remove(pontos.size()-1);
        Log.v("assaasd",Integer.toString(pontos.size()));
        markers.get(markers.size()-1).remove();
        markers.remove(markers.size()-1);
        for(int i =0;i<list.size();i++)
        list.get(i).remove();
        aplicaPolyline();

    }

    public void botaoAddPonto(View v){
        String addressPonto = etPonto.getText().toString();
        progressDialog = ProgressDialog.show(AdicionarPontos.this,"Adicionando Ponto","Aguarde");
        pontoLatLng = c.addressToLatLng(addressPonto);
        progressDialog.cancel();
        LatLng ponto = new LatLng(pontoLatLng[0],pontoLatLng[1]);
        markers.add(mMap.addMarker(new MarkerOptions().position(ponto).title("Origem")/*.draggable(true)*/));
        pontos.add(ponto);
        aplicaPolyline();
    }
    public void botaoFinalizarPonto(View v){
        LatLng ponto = new LatLng(rota.getDestinoLat(),rota.getDestinoLng());
        markers.add(mMap.addMarker(new MarkerOptions().position(ponto).title("Origem")/*.draggable(true)*/));
        pontos.add(ponto);
        aplicaPolyline();
        rota.setPontos(pontos);
//        rota.setMarkers(markers);
        Intent intent = new Intent(this,FinalizarCadastroRota.class);
        startActivity(intent);

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.v("assa",marker.getId());
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.v("assa",marker.getId());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.v("assa",marker.getId());
    }
}