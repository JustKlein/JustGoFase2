package com.example.justgo.Drawer;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.R;
import com.example.justgo.Requests.GetPontoRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapaMostratExperiencia extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   int codRota;
    List<PolylineOptions> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_mostrat_experiencia);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        System.out.println(codRota);
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


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PolylineOptions rectOptions;
                    rectOptions = new PolylineOptions();
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int i=0;i<jsonResponse.length();i++) {
                        LatLng ponto =new LatLng(jsonResponse.getJSONArray(i).getDouble(2), jsonResponse.getJSONArray(i).getDouble(3));
                        rectOptions.add(ponto);
                    }
                    mMap.addPolyline(rectOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rectOptions.getPoints().get(0),11 ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Bundle bundle = getIntent().getExtras();
        codRota = bundle.getInt("trecho");
        System.out.println("DE NOVO" + codRota);
        GetPontoRequest getPontoRequest = new GetPontoRequest(codRota, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapaMostratExperiencia.this);
        queue.add(getPontoRequest);

    }
}
