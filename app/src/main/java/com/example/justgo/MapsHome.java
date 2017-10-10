package com.example.justgo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.CadastroRota.NovaRota;
import com.example.justgo.Requests.Experiencia.GetPontosHomePageRequest;
import com.example.justgo.Requests.HomePageRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsHome extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    ArrayList pontosDentrodoRaio;
    List<PolylineOptions> polylines;
    private GoogleApiClient googleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pontosDentrodoRaio = new ArrayList<Integer>();
        polylines = new ArrayList<PolylineOptions>();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
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
    public String converterArraytoString(ArrayList<Integer> array) {
        String pontos = "";

        for (int i = 0; i < array.size(); i++) {
            pontos = pontos.concat(array.get(i).toString());
            if (i != array.size() - 1)
                pontos = pontos.concat(":");
        }
        Log.v("aslads", pontos);
        return pontos;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        pontosDentrodoRaio.add(new Integer(jsonResponse.getJSONArray(i).getInt(0)));
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                PolylineOptions rectOptions;

                                JSONArray jsonResponse = new JSONArray(response);
                                for (int i = 0; i < jsonResponse.length(); i++) {
                                    rectOptions = new PolylineOptions();
                                    System.out.println(jsonResponse.getJSONArray(i));
                                    JSONArray json = jsonResponse.getJSONArray(i);
                                    System.out.println(json.length());
                                    for (int j = 0; j < json.length(); j++) {
                                      /*  rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3))).width(5);
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));*/
                                      Log.v("asdasdaas","dasdasdas");
                                      mMap.addMarker(new MarkerOptions().position(new LatLng(json.getJSONArray(j).getDouble(2),json.getJSONArray(j).getDouble(3))));
                                    }
                                    //mMap.addPolyline(rectOptions);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    GetPontosHomePageRequest getPontoRequest = new GetPontosHomePageRequest(converterArraytoString(pontosDentrodoRaio), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MapsHome.this);
                    queue.add(getPontoRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HomePageRequest homePageRequest = new HomePageRequest(-19.8986831, -44.0293941, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapsHome.this);
        queue.add(homePageRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        pararConexaoComGoogleApi();
    }

    public void pararConexaoComGoogleApi() {
        //Verificando se está conectado para então cancelar a conexão!
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /**
     * Depois que o método connect() for chamado, esse método será chamado de forma assíncrona caso a conexão seja bem sucedida.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("CONECTOU");
        //Google API connection has been done successfully
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the   user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
            // Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            //System.out.println(lastLocation.getLatitude());

            return;
        }
        // Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        // System.out.println(lastLocation.getLatitude());

    }

    /**
     * Esse método é chamado quando o client está temporariamente desconectado. Isso pode acontecer quando houve uma falha ou problema com o serviço que faça com que o client seja desligado.
     * Nesse estado, todas as requisições e listeners são cancelados.
     * Não se preocupe em tentar reestabelecer a conexão, pois isso acontecerá automaticamente.
     * As aplicações devem desabilitar recursos visuais que estejam relacionados com o uso dos serviços e habilitá-los novamente quando o método onConnected() for chamado, indicando reestabelecimento da conexão.
     */
    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("TILTOO");
    }

    /**
     * Método chamado quando um erro de conexão acontece e não é possível acessar os serviços da Google Service.
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("DEU MERDA");
        pararConexaoComGoogleApi();
    }

    public void onClickAddRota(View v) {
        Intent Address = new Intent(this, NovaRota.class);
        startActivity(Address);
    }
}

