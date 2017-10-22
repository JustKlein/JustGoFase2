package com.example.justgo.Drawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.Requests.ExcluirRota;
import com.example.justgo.Requests.Experiencia.GetCaracteristicasPontoRequest;
import com.example.justgo.Requests.Experiencia.GetRotaRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MostrarExperiencia extends AppCompatActivity {
    public static int COD_PONTO = 0,COD_ROTA = 1,ORIGEM = 2,DESTINO=3,DESCRICAO=4,TEMPO=5,PRECO=6,MEIO_DE_TRANSPORTE=7;
    int codRota;
    TextView tVNomeRota, tVOrigem, tVDestino, tVTempoGasto, tVValorGasto, tVDescricao;
    ListView listViewPontosdaRota;
    Conversor conversor;
    ArrayList<TrechoItem> trechoItem;
    int[] lista = new int[]{R.drawable.grid1,R.drawable.grid2,R.drawable.grid3,R.drawable.grid4,R.drawable.grid5,R.drawable.grid6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_experiencia);
        Bundle bundle = getIntent().getExtras();
        codRota = bundle.getInt("codRota");
        System.out.print("ROTAAA" + codRota);
        tVNomeRota = (TextView) findViewById(R.id.nomeRotaMostrarExperiencia);
        tVOrigem = (TextView) findViewById(R.id.origemMostrarExeriencia);
        tVDestino = (TextView) findViewById(R.id.destinoMostrarExperiencia);
        //tVTempoGasto = (TextView) findViewById(R.id.tempoGastoMostrarExperiencia);
        //tVValorGasto = (TextView) findViewById(R.id.valorGastoMostrarExeriencia);
        tVDescricao = (TextView) findViewById(R.id.descricaoMostrarExeriencia);
        listViewPontosdaRota = (ListView) findViewById(R.id.listViewPontosMostrarExperiencia);
        conversor = new Conversor(getApplicationContext());
        trechoItem = new ArrayList<TrechoItem>();
        /*GridView grid = (GridView) findViewById(R.id.gridView);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        grid.setLayoutParams(lp);
        grid.setNumColumns(GridView.AUTO_FIT);
        grid.setColumnWidth((int)getResources().getDimension(R.dimen.sessenta_dp));
        grid.setVerticalSpacing((int)getResources().getDimension(R.dimen.dez_dp));
        grid.setHorizontalSpacing((int)getResources().getDimension(R.dimen.dez_dp));


        grid.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;498
            }
        });*/
        setarDadosRota();
        setarDadosPontos();
    }

    public void setarDadosRota() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    // boolean success = jsonResponse.getJSONArray(0).get("success");
                    String email = jsonResponse.getJSONArray(0).getString(1);
                    String nomeRota = jsonResponse.getJSONArray(0).getString(2);
                    LatLng origem = new LatLng(jsonResponse.getJSONArray(0).getDouble(3), jsonResponse.getJSONArray(0).getDouble(4));
                    LatLng destino = new LatLng(jsonResponse.getJSONArray(0).getDouble(5), jsonResponse.getJSONArray(0).getDouble(6));
                    String descricao = jsonResponse.getJSONArray(0).getString(7);
                    Log.v("Email", email);

                    tVNomeRota.setText(nomeRota);
                    tVOrigem.setText(conversor.latLngtoAddress(origem.latitude, origem.longitude));
                    tVDestino.setText(conversor.latLngtoAddress(destino.latitude, destino.longitude));
                    tVDescricao.setText(descricao);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetRotaRequest getRotaRequest = new GetRotaRequest(codRota, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MostrarExperiencia.this);
        queue.add(getRotaRequest);
    }
    public void setarDadosPontos(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Log.v("asda",jsonResponse.toString());
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        int codPonto = jsonResponse.getJSONArray(i).getInt(COD_PONTO);
                        String enderecoOrigem = jsonResponse.getJSONArray(i).getString(ORIGEM);
                        String enderecoDestino = jsonResponse.getJSONArray(i).getString(DESTINO);
                        String descricao = jsonResponse.getJSONArray(i).getString(DESCRICAO);
                        double _tempo = jsonResponse.getJSONArray(i).getDouble(TEMPO);
                        float tempo = (float)_tempo;
                        double _preco = jsonResponse.getJSONArray(i).getDouble(PRECO);
                        float preco = (float) _preco;
                        String meiodeTransporte = jsonResponse.getJSONArray(i).getString(MEIO_DE_TRANSPORTE);
                        trechoItem.add(new TrechoItem(codPonto,enderecoOrigem,enderecoDestino,descricao,tempo,preco,meiodeTransporte));

                        /*PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(
                                mGoogleApiClient, placeId);
                        PlaceBuffer placeBuffer = result.await();*/
                        Log.v("TEMANHO","entrou");
                    }
                    list(trechoItem);
                    Log.v("TEMANHO", trechoItem.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        GetCaracteristicasPontoRequest getCaracteristicasPontoRequest = new GetCaracteristicasPontoRequest(codRota, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MostrarExperiencia.this);
        queue.add(getCaracteristicasPontoRequest);
    }

    public void list(ArrayList<TrechoItem> agenda){
        Log.v("asnkjasj",Integer.toString(agenda.size()));
        TrechoItemAdapter adapter = new TrechoItemAdapter(this, agenda);
        listViewPontosdaRota = (ListView) findViewById(R.id.listViewPontosMostrarExperiencia);
        listViewPontosdaRota.setAdapter(adapter);

    }
    public void verMapaMostrarExeperiencia(View v){
        Intent intent = new Intent(MostrarExperiencia.this, MapaMostratExperiencia.class);
        intent.putExtra("trecho", codRota);
        startActivity(intent);
    }
    public void excluirRota(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MostrarExperiencia.this);
        builder.setMessage("Você tem certeza que deseja excluir essa rota?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    Boolean success = jsonResponse.getBoolean("success");
                                    if(success)
                                    {
                                        MostrarExperiencia.super.onBackPressed();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        ExcluirRota excluirRota = new ExcluirRota(codRota, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MostrarExperiencia.this);
                        queue.add(excluirRota);
                    }
                })
                .create()
                .show();

    }
}
