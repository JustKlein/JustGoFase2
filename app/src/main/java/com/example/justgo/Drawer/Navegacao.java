package com.example.justgo.Drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.CadastroRota.NovaRota;
import com.example.justgo.MapsHome;
import com.example.justgo.R;
import com.example.justgo.Requests.Experiencia.GetPontosHomePageRequest;
import com.example.justgo.Requests.HomePageRequest;
import com.example.justgo.Sobre;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Navegacao extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    MapView mapView;
    private GoogleMap mMap;
    ArrayList pontosDentrodoRaio;
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
        Log.v("aslads", pontos);
        return pontos;
    }
    public int randomColor() {
        return Color.rgb((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3))).width(5).color(randomColor()).width(5);
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));
                                        rectOptions.add(new LatLng(json.getJSONArray(j).getDouble(2), json.getJSONArray(j).getDouble(3)));

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

    }

}