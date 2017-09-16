package com.example.justgo.CadastroRota;

import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class NovaRota extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText origem, destino;
    private Conversor c;
    private Double[] arraydeTeste;
    private Double[] arrayOrigem;
    private Double[] arrayDestino;
    public static RotaAux origemDestino;
    PolylineOptions rectOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_rota);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        origem = (EditText) findViewById(R.id.origem);
        destino = (EditText) findViewById(R.id.destino);

        c = new Conversor(getApplicationContext());
        arrayOrigem = new Double[2];
        arrayDestino = new Double[2];
        arraydeTeste = new Double[2];
        rectOptions = new PolylineOptions();

    }

    public void BuscarOrigemDestino(View v) {
        if (origem != null && destino != null) {
            String addressOrigem = origem.getText().toString();
            String addressDestino = destino.getText().toString();
            arraydeTeste = c.addressToLatLng("Rua vergara 755");
            arrayOrigem = c.addressToLatLng(addressOrigem);
            Log.v("LATITUDE", arrayOrigem[0].toString());
            Log.v("LONGITUDE", arrayOrigem[1].toString());
            arrayDestino = c.addressToLatLng(addressDestino);
            Log.v("LATITUDE", arrayDestino[0].toString());
            Log.v("LONGITUDE", arrayDestino[1].toString());
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(arrayOrigem[0], arrayOrigem[1]);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            rectOptions.add(sydney);
            sydney = new LatLng(arrayDestino[0], arrayDestino[1]);
            rectOptions.add(sydney);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(icon));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
            Polyline polyline = mMap.addPolyline(rectOptions);
        }
    }
    public void ConfirmarOrigemDestino(View v){
        if(arrayDestino != null && arrayOrigem!=null){
        String addressOrigem = origem.getText().toString();
        String addressDestino = destino.getText().toString();
        arrayOrigem = c.addressToLatLng(addressOrigem);
        Log.v("LATITUDE",arrayOrigem[0].toString());
        Log.v("LONGITUDE",arrayOrigem[1].toString());
        arrayDestino = c.addressToLatLng(addressDestino);
        Log.v("LATITUDE",arrayDestino[0].toString());
        Log.v("LONGITUDE",arrayDestino[1].toString());


            origemDestino = new RotaAux();
            origemDestino.setOrigemLat(arrayOrigem[0]);
            origemDestino.setOrigemLng(arrayOrigem[1]);
            origemDestino.setDestinoLat(arrayDestino[0]);
            origemDestino.setDestinoLng(arrayDestino[1]);
            Intent intent = new Intent(this,AdicionarPontos.class);
            startActivity(intent);
        }

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }
}
