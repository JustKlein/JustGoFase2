package com.justgo.Drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.justgo.CadastroRota.NovaRota;
import com.justgo.R;
import com.justgo.Requests.Experiencia.GetPontosHomePageRequest;
import com.justgo.Requests.GetRotaLatLng;
import com.justgo.Requests.HomePageRequest;
import com.justgo.Sobre;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Navegacao extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    MapView mapView;
    private GoogleMap mMap;
    List<Marker> markersOnMap;
    ArrayList pontosDentrodoRaio;
    ArrayList pontosSolicitados;
    ArrayList pontosProximosaoSolicitado;
    List<PolylineOptions> polylines;
    private GoogleApiClient googleApiClient;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mapView = (MapView) findViewById(R.id.mapHome);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //DECLARAÇÕES
        pontosDentrodoRaio = new ArrayList<Integer>();
        pontosSolicitados = new ArrayList<Integer>();
        pontosProximosaoSolicitado = new ArrayList<Double>();
        markersOnMap = new ArrayList<Marker>();
        polylines = new ArrayList<PolylineOptions>();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sobre) {
            Intent sobre = new Intent(this, Sobre.class);
            startActivity(sobre);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_experiencia) {
            // abrir experiencias
            Intent exp = new Intent(this, Experiencias.class);
            startActivity(exp);
        } else if (id == R.id.nav_config) {
            //abrir as configuracoes
            Intent config = new Intent(this, Configuracoes.class);
            startActivity(config);

            //setContentView(R.layout.activity_configuracoes);
            return true;
        } else if (id == R.id.nav_sair) {
            // deslogar
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickAddRota(View v) {
        Intent homepage =  new Intent(this, NovaRota.class);
        startActivity(homepage);


    }

    public String converterArraytoString(ArrayList<Integer> array) {
        String pontos = "";

        for (int i = 0; i < array.size(); i++) {
            pontos = pontos.concat(array.get(i).toString());
            if (i != array.size() - 1)
                pontos = pontos.concat(":");
        }
        return pontos;
    }
    public String converterArrayDoubletoString(ArrayList<Double> array) {
        String pontos = "";

        for (int i = 0; i < array.size(); i++) {
            pontos = pontos.concat(array.get(i).toString());
            if (i != array.size() - 1)
                pontos = pontos.concat(":");
        }
        return pontos;
    }
    public int randomColor() {
        return Color.rgb((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        progressDialog = ProgressDialog.show(Navegacao.this, "CarregandoPontos", "Aguarde");
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
                                       /* rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3))).width(5).color(randomColor()).width(5);
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));*/
                                        markersOnMap.add( mMap.addMarker(new MarkerOptions().position(new LatLng(json.getJSONArray(j).getDouble(2),json.getJSONArray(j).getDouble(3)))));


                                    }
                                    progressDialog.cancel();
                                    mMap.addPolyline(rectOptions);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    GetPontosHomePageRequest getPontoRequest = new GetPontosHomePageRequest(converterArraytoString(pontosDentrodoRaio), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Navegacao.this);
                    queue.add(getPontoRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HomePageRequest homePageRequest = new HomePageRequest(-19.8986831, -44.0293941, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Navegacao.this);
        queue.add(homePageRequest);
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-19.8986831, -44.0293941),8));
    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            pontosProximosaoSolicitado.clear();
            pontosSolicitados.clear();
            float results[] = new float[1];
            for(int i =0;i<markersOnMap.size();i++){
                Location.distanceBetween(marker.getPosition().latitude,marker.getPosition().longitude,markersOnMap.get(i).getPosition().latitude,markersOnMap.get(i).getPosition().longitude,results);
                if(results[0]<1000){
                    pontosProximosaoSolicitado.add(markersOnMap.get(i).getPosition().latitude);
                    pontosProximosaoSolicitado.add(markersOnMap.get(i).getPosition().longitude);
                }
            }
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonResponse = new JSONArray(response);
                        Log.v("PONTOS QUE VOLTAM:",jsonResponse.toString());
                        desenharRotaSolicitada(jsonResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            GetRotaLatLng getRotaLatLng = new GetRotaLatLng(converterArrayDoubletoString(pontosProximosaoSolicitado), responseListener);
            RequestQueue queue = Volley.newRequestQueue(Navegacao.this);
            queue.add(getRotaLatLng);
            return false;
        }
    });
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Intent intent = new Intent(Navegacao.this,MostrarExperiencia.class);
                intent.putExtra("codRota", Integer.parseInt(polyline.getTag().toString()));
                startActivity(intent);
                Log.v("A CARALHO", polyline.getTag().toString());
            }
        });
    }
    public void desenharRotaSolicitada(JSONArray jsonResponse) throws JSONException {
        mMap.clear();
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONArray json = jsonResponse.getJSONArray(i);
            for(int j =0;j<json.length();j++){
                pontosSolicitados.add(new Integer(json.getJSONArray(j).getInt(0)));
            }

        }
        Log.v("OS PONTOS SOLICITADOS:",pontosSolicitados.toString());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PolylineOptions rectOptions;

                    JSONArray jsonResponse = new JSONArray(response);
                    Log.v("JSON",jsonResponse.toString());
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        rectOptions = new PolylineOptions();
                        System.out.println(jsonResponse.getJSONArray(i));
                        JSONArray json = jsonResponse.getJSONArray(i);
                        System.out.println(json.length());
                        for (int j = 0; j < json.length(); j++) {
                            rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3))).width(5).color(randomColor()).width(5);

                        }
                        progressDialog.cancel();
                        Polyline polyline = mMap.addPolyline(rectOptions);
                        polyline.setTag(jsonResponse.getJSONArray(i).getJSONArray(0).getInt(1));
                        polyline.setClickable(true);
                        Log.v("look",polyline.getTag().toString());
                        List<LatLng> teste = polyline.getPoints();
                        //onMapReady(mMap);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetPontosHomePageRequest getPontoRequest = new GetPontosHomePageRequest(converterArraytoString(pontosSolicitados), responseListener);
        RequestQueue queue = Volley.newRequestQueue(Navegacao.this);
        queue.add(getPontoRequest);

    }
}