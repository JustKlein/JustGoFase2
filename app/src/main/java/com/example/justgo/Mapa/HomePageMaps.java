package com.example.justgo.Mapa;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.R;
import com.example.justgo.Requests.GetPontoRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

public class HomePageMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONArray jsonResponse = new JSONArray(response);
//
//                    // boolean success = jsonResponse.getBoolean("success");rue
//                    if (true) {
//                        for (i = 0;i<=jsonResponse.length() ; i++) {
//                            Double latitude = jsonResponse.getJSONArray(i).getDouble(1);
//                            Double longitude = jsonResponse.getJSONArray(i).getDouble(2);
//                            LatLng sydney = new LatLng(latitude, longitude);
//                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
//                            Log.v("AAAAAAAAAAAAA", latitude.toString());
//                        }
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageMaps.this);
//                        builder.setMessage("DEU ERRADO")
//                                .setNegativeButton("Tentar Novamente", null)
//                                .create()
//                                .show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        GetPontoRequest getPontoRequest = new GetPontoRequest("teste", responseListener);
//        RequestQueue queue = Volley.newRequestQueue(HomePageMaps.this);
//        queue.add(getPontoRequest);

    }

    public void BD(String email, String senha) {

    }
}
