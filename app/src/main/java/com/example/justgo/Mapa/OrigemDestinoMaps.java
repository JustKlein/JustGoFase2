package com.example.justgo.Mapa;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.justgo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.example.justgo.CriarRota.origemDestino;

public class OrigemDestinoMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double origemLat,origemLng,destinoLat,destinoLng;
    PolylineOptions rectOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origem_destino_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rectOptions = new PolylineOptions();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng origem = new LatLng(origemDestino.getOrigemLat(),origemDestino.getOrigemLng()) ;
        mMap.addMarker(new MarkerOptions().position(origem).title("Marker in Sydney"));
        LatLng destino = new LatLng(origemDestino.getDestinoLat(),origemDestino.getDestinoLng()) ;
        mMap.addMarker(new MarkerOptions().position(destino).title("Marker in Sydney"));
        rectOptions.add(origem);
        rectOptions.add(destino);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(origem));
        Polyline polyline = mMap.addPolyline(rectOptions);
    }


}
