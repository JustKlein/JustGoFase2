package com.example.justgo.Drawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.Requests.Experiencia.GetCaracteristicasPontoRequest;
import com.example.justgo.Requests.Experiencia.GetRotaRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MostrarExperiencia extends AppCompatActivity {
    public static int CODPONTO = 0,CODROTA=1,LATITUDE=2,LONGITUDE=3,NROPONTO=4,DESCRICAO=5,TEMPO=6,PRECO=7,MEIODETRANSPORTE=8;
    int codRota;
    TextView tVNomeRota, tVOrigem, tVDestino, tVTempoGasto, tVValorGasto, tVDescricao;
    ListView listViewPontosdaRota;
    Conversor conversor;
    ArrayList<TrechoItem> trechoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_experiencia);
        Bundle bundle = getIntent().getExtras();
        codRota = bundle.getInt("codRota");
        tVNomeRota = (TextView) findViewById(R.id.nomeRotaMostrarExperiencia);
        tVOrigem = (TextView) findViewById(R.id.origemMostrarExeriencia);
        tVDestino = (TextView) findViewById(R.id.destinoMostrarExperiencia);
        tVTempoGasto = (TextView) findViewById(R.id.tempoGastoMostrarExperiencia);
        tVValorGasto = (TextView) findViewById(R.id.valorGastoMostrarExeriencia);
        tVDescricao = (TextView) findViewById(R.id.descricaoMostrarExeriencia);
        listViewPontosdaRota = (ListView) findViewById(R.id.listViewPontosMostrarExperiencia);
        conversor = new Conversor(getApplicationContext());
        trechoItem = new ArrayList<TrechoItem>();
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

                    for (int i = 0; i < jsonResponse.length() - 1; i++) {
                        int codPonto = Integer.parseInt(Integer.toString(jsonResponse.getJSONArray(i).getInt(CODROTA)).concat(Integer.toString(jsonResponse.getJSONArray(i).getInt(NROPONTO))));
                        LatLng origem = new LatLng(jsonResponse.getJSONArray(i).getDouble(LATITUDE),jsonResponse.getJSONArray(i).getDouble(LONGITUDE));
                        LatLng destino = new LatLng(jsonResponse.getJSONArray(i+1).getDouble(LATITUDE),jsonResponse.getJSONArray(i+1).getDouble(LONGITUDE));

                        trechoItem.add(new TrechoItem(codPonto,jsonResponse.getJSONArray(i).getInt(CODROTA),origem, destino,jsonResponse.getJSONArray(i).getInt(NROPONTO),jsonResponse.getJSONArray(i).getString(DESCRICAO),jsonResponse.getJSONArray(i).getString(TEMPO),jsonResponse.getJSONArray(i).getDouble(PRECO),jsonResponse.getJSONArray(i).getString(MEIODETRANSPORTE)));

                    }
                   /* LatLng origemLatLng = new LatLng(Double.parseDouble(jsonResponse.getJSONArray(jsonResponse.length()-1).getString(2)), Double.parseDouble(jsonResponse.getJSONArray(jsonResponse.length()-1).getString(3)));
                    pontoItem.add(new PontoItem(jsonResponse.getJSONArray(jsonResponse.length()-1).getInt(0),jsonResponse.getJSONArray(jsonResponse.length()-1).getInt(4), c.latLngtoAddress(origemLatLng.latitude,origemLatLng.longitude),null,codRota));*/
                    list(trechoItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.v("O XENTE",Integer.toString(codRota));

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

}
