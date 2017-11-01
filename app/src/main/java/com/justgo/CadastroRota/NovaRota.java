package com.justgo.CadastroRota;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.justgo.LogineCadastro.UsuarioLogadoSingleton;
import com.justgo.R;

import java.util.ArrayList;


public class NovaRota extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ArrayList<Place> places;
    PolylineOptions rectOptions;
    BitmapDescriptor icon;
    EditText ed;
    PlaceAutocompleteFragment autocompleteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_rota);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        places = new ArrayList<Place>();
        rectOptions = new PolylineOptions();
        icon = BitmapDescriptorFactory.fromResource(R.drawable.ponto_no_mapa);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ed = (EditText) findViewById(R.id.autocomplete_places);
        autocompleteFragment.setHint("Digite um ponto da rota");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                Log.i("QQ", "Place: " + place.getName());
                adicionarPonto(place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i("QQ", "Ocorreu um erro: " + status);
            }
        });
    }

    public void adicionarPonto(Place place){

        mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
        places.add(place);
        aplicaPolyline();
    }
    public void voltarPonto(View view){
        if(places.size()>0) {
            Log.v("antes",Integer.toString(places.size()-1));
            places.remove(places.size() - 1);
            Log.v("depois",Integer.toString(places.size()-1));
            aplicaPolyline();
        }
        else{
            if (places.size()==0){
                places.clear();
            }
        }
    }
    public void aplicaPolyline(){
        mMap.clear();
        if(places.size()>0) {
            PolylineOptions rectOptions;
            rectOptions = new PolylineOptions();
            Log.v("Aplica", Integer.toString(places.size() - 1));
            for (int i = 0; i < places.size(); i++) {
                rectOptions.add(places.get(i).getLatLng()).width(5).color(Color.BLUE);
                mMap.addMarker(new MarkerOptions().position(places.get(i).getLatLng()).icon(icon));
            }
            mMap.addPolyline(rectOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(places.get(places.size() - 1).getLatLng(), 14));
        }
    }
    public void confirmarRota(View view){
        if(!places.isEmpty()) {
            UsuarioLogadoSingleton usuarioLogado = UsuarioLogadoSingleton.getInstancia();

            CadastrodeRota cadastrarRota = new CadastrodeRota();
            try {
                cadastrarRota.cadastrarnaTabelaRota(usuarioLogado.getEmail(), places, getApplicationContext());
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }



            Intent intent = new Intent(NovaRota.this, FinalizarCadastroRota.class);
            startActivity(intent);
        }
        //Log.v("FUNCIONOU",Integer.toString(rotaSingleton.getCodRota()));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
