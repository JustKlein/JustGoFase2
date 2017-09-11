package com.example.justgo.CadastroRota;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Drawer.Navegacao;
import com.example.justgo.LogineCadastro.UsuarioLogado;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.Requests.FinalizarCadastroPontoRequest;
import com.example.justgo.Requests.FinalizarCadastroRotaRequest;
import com.example.justgo.Requests.GetPontoRequest;
import com.example.justgo.Requests.RegisterRequest;
import com.example.justgo.Requests.UltimaRotaAddRequest;
import com.example.justgo.Requests.UpdateRotaRequest;
import com.example.justgo.teste;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalizarCadastroRota extends AppCompatActivity {
    RotaAux rota;
    UsuarioLogado usuarioLogado;
   teste t;
    int IDCODROTA;
    private List<LatLng> pontosEntrada;
    private List<LatLng> pontosRetorno;
    ArrayList<PontoItem> pontoItem;
    ListView listView;
    Conversor c;
    JSONArray j;
    public int oi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_cadastro_rota);
        pontosEntrada = rota.getPontos();
        pontosRetorno = new ArrayList<LatLng>();
        pontoItem = new ArrayList<PontoItem>();
        Log.v("PontosEntrada",pontosEntrada.toString());
        c =  new Conversor(getApplicationContext());
        cadastrarRotanoBD();
    }

    public void cadastrarRotanoBD(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    final int idRota = jsonResponse.getInt("id") - 1;
                    //Log.v("assaasa",Integer.toString(idRota));
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Usuário cadastrado com sucesso")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();
                                     Log.v("PONTOS",Integer.toString(pontosEntrada.size()));
                                   for(int i = 0; i < pontosEntrada.size();i++) {
                                      cadastrarPontosnoBD(idRota, pontosEntrada.get(i).latitude, pontosEntrada.get(i).longitude, i+1);
                                }
                        ultimaRotaAdd();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Erro ao cadastrar usuário")
                                .setNegativeButton("Tentar novamente", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        FinalizarCadastroRotaRequest finalizarCadastroRotaRequest = new FinalizarCadastroRotaRequest("Rota sem Nome",usuarioLogado.getUsuario(),rota.getOrigemLat().toString(),
                rota.getOrigemLng().toString(),rota.getDestinoLat().toString(), rota.getDestinoLng().toString(),responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(finalizarCadastroRotaRequest);
    }
    public void cadastrarPontosnoBD(final int codRota, Double latitude, Double longitude, int nroPonto){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        Log.v("ENTOU","EEE");
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Erro ao cadastrar usuário")
                                .setNegativeButton("Tentar novamente", null)
                                .create()
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        int codPonto = Integer.parseInt(Integer.toString(codRota).concat(Integer.toString(nroPonto)));
        FinalizarCadastroPontoRequest finalizarCadastroPontoRequest = new FinalizarCadastroPontoRequest(codPonto,codRota,latitude,longitude,nroPonto,responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(finalizarCadastroPontoRequest);
    }

    public void ultimaRotaAdd(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String id = jsonResponse.getString("idResposta");
                        Log.v("ID", id);
                        editarPontos(Integer.parseInt(id));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Não foi possível efetuar o Login")
                                .setNegativeButton("Tentar Novamente", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UltimaRotaAddRequest ultimaRotaAddRequest = new UltimaRotaAddRequest(usuarioLogado.getUsuario(),responseListener);
        Log.v("ASSSSSSASSASS", usuarioLogado.getUsuario());
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(ultimaRotaAddRequest);
    }

    public void editarPontos(final int codRota) {
        IDCODROTA = codRota;
        Log.v("VELHO DO CEU", Integer.toString(codRota));
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                    try {
                        final JSONArray jsonResponse = new JSONArray(response);
                        Log.v("saadsassa", Integer.toString(jsonResponse.length()));
                        for (int i = 0; i < jsonResponse.length() - 1; i++) {
                            LatLng origemLatLng = new LatLng(Double.parseDouble(jsonResponse.getJSONArray(i).getString(2)), Double.parseDouble(
                                    jsonResponse.getJSONArray(i).getString(3)));
                            LatLng destinoLatLng = new LatLng(Double.parseDouble(jsonResponse.getJSONArray(i + 1).getString(2)), Double.parseDouble(jsonResponse.getJSONArray(i + 1).getString(3)));
                            Log.v("LALALALALA", c.latLngtoAddress(origemLatLng.latitude, origemLatLng.longitude));
                            pontoItem.add(new PontoItem(jsonResponse.getJSONArray(i).getInt(0),jsonResponse.getJSONArray(i).getInt(4), c.latLngtoAddress(origemLatLng.latitude, origemLatLng.longitude),
                                    c.latLngtoAddress(destinoLatLng.latitude, destinoLatLng.longitude), codRota));
                        }
                        LatLng origemLatLng = new LatLng(Double.parseDouble(jsonResponse.getJSONArray(jsonResponse.length()-1).getString(2)), Double.parseDouble(jsonResponse.getJSONArray(jsonResponse.length()-1).getString(3)));
                        pontoItem.add(new PontoItem(jsonResponse.getJSONArray(jsonResponse.length()-1).getInt(0),jsonResponse.getJSONArray(jsonResponse.length()-1).getInt(4), c.latLngtoAddress(origemLatLng.latitude,origemLatLng.longitude),null,codRota));
                        list(pontoItem);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                Log.v("CLICOU EM: ", Integer.toString(position));
                                Intent intent = new Intent(FinalizarCadastroRota.this, EditarPonto.class);
                                try {
                                    intent.putExtra("codPonto", jsonResponse.getJSONArray(position).getInt(0));
                                    intent.putExtra("codRota", jsonResponse.getJSONArray(position).getInt(1));
                                    intent.putExtra("nroPonto", jsonResponse.getJSONArray(position).getInt(4));
                                    //continuar = false;
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //  intent.putExtra("codPonto",);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

        };

        GetPontoRequest getPontoRequest = new GetPontoRequest(codRota, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(getPontoRequest);
    }

    public void list(ArrayList<PontoItem> agenda){
        Log.v("asnkjasj","FILHA DE UMA PUTA");
        PontoItemAdapter adapter = new PontoItemAdapter(this, agenda);
        listView = (ListView) findViewById(R.id.listViewdePontos);
        listView.setAdapter(adapter);

    }
    public void botaoFinalizarCadastroRotaePontos (View v){
        Log.v("asjdasjkld",Integer.toString(IDCODROTA));
        EditText eTNomeRota = (EditText) findViewById(R.id.nomeRota);
        EditText eTDescricao = (EditText) findViewById(R.id.descricaoRota);
        String nomeRota = eTNomeRota.getText().toString();
        String descricao = eTDescricao.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(getApplicationContext(),Navegacao.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Erro ao cadastrar")
                                .setNegativeButton("Tentar novamente", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UpdateRotaRequest updateRotaRequest = new UpdateRotaRequest(IDCODROTA,nomeRota,descricao,responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(updateRotaRequest);

    }

}
