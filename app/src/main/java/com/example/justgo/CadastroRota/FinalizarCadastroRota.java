package com.example.justgo.CadastroRota;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Drawer.Navegacao;
import com.example.justgo.LogineCadastro.UsuarioLogadoSingleton;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.Requests.GetPontoRequest;
import com.example.justgo.Requests.RegisterRequest;
import com.example.justgo.Requests.UltimaRotaAddRequest;
import com.example.justgo.Requests.UpdateRotaRequest;
import com.example.justgo.teste;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class FinalizarCadastroRota extends AppCompatActivity {
    CadastrodeRota cadastrarRota;
    ListView listView;
    PontoItemAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<PontoItem> pontoItem;RotaSingleton rotaSingleton;
    private static int COD_PONTO = 0, PLACE_NOME = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_cadastro_rota);
        rotaSingleton = RotaSingleton.getInstancia();
        Log.v("OI",Integer.toString(rotaSingleton.getCodRota()));
        pontoItem = new ArrayList<PontoItem>();
        editarPontos();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("asddddasdasd","aaaaaaaaaa");
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("Resposta",-1);
                rotaSingleton.positions.add(position);
                //adapter.setmSelectedItem();
                adapter.notifyDataSetChanged();
                Log.v("DEU CERTO", Integer.toString(position));
        }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void editarPontos() {

        RotaSingleton rotaSingleton = RotaSingleton.getInstancia();
        progressDialog = ProgressDialog.show(FinalizarCadastroRota.this, "Carregando Pontos", "Aguarde");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final JSONArray jsonResponse = new JSONArray(response);
                    Log.v("saadsassa", Integer.toString(jsonResponse.length()));
                    for (int i = 0; i < jsonResponse.length() - 1; i++) {
                        int codPonto = jsonResponse.getJSONArray(i).getInt(COD_PONTO);
                        String enderecoOrigem = jsonResponse.getJSONArray(i).getString(PLACE_NOME);
                        String enderecoDestino = jsonResponse.getJSONArray(i+1).getString(PLACE_NOME);
                        pontoItem.add(new PontoItem(codPonto,enderecoOrigem,enderecoDestino));
                        /*PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(
                                mGoogleApiClient, placeId);
                        PlaceBuffer placeBuffer = result.await();*/
                    }
                    list(pontoItem);
                    progressDialog.cancel();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            Log.v("CLICOU EM: ", Integer.toString(position));
                            Intent intent = new Intent(FinalizarCadastroRota.this, EditarPonto.class);
                            try {
                                intent.putExtra("codPonto", jsonResponse.getJSONArray(position).getInt(COD_PONTO));
                                intent.putExtra("origem",pontoItem.get(position).getOrigem());
                                intent.putExtra("destino", pontoItem.get(position).getDestino());
                                intent.putExtra("posicao",position);
                                startActivityForResult(intent,1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };


     Log.v("COD ROTA", Integer.toString(rotaSingleton.getCodRota()));
        GetPontoRequest getPontoRequest = new GetPontoRequest(rotaSingleton.getCodRota(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(getPontoRequest);
    }
    public void list(ArrayList<PontoItem> agenda){
        adapter = new PontoItemAdapter(this, agenda);
        listView = (ListView) findViewById(R.id.listViewdePontos);
        listView.setAdapter(adapter);
    }

    public void botaoFinalizarCadastroRotaePontos(View v) {
        EditText eTNomeRota = (EditText) findViewById(R.id.nomeRota);
        EditText eTDescricao = (EditText) findViewById(R.id.descricaoRota);
        String nomeRota = eTNomeRota.getText().toString();
        String descricao = eTDescricao.getText().toString();
        super.onPause();
        progressDialog.cancel();
        CadastrodeRota cadastrodeRota = new CadastrodeRota();
        cadastrodeRota.finalizarCadastroRota(getApplicationContext(),nomeRota,descricao);
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), Navegacao.class);
        startActivity(intent);


    }

}


/*
public class FinalizarCadastroRota extends AppCompatActivity {
    RotaAux rota;
    UsuarioLogado usuarioLogado;
    teste t;
    ProgressDialog progressDialog;
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
        Log.v("PontosEntrada", pontosEntrada.toString());
        c = new Conversor(getApplicationContext());
        cadastrarRotanoBD();
    }

    public void cadastrarRotanoBD() {
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
                        Log.v("PONTOS", Integer.toString(pontosEntrada.size()));
                        progressDialog = ProgressDialog.show(FinalizarCadastroRota.this, "CadastrandoPontos", "Aguarde");
                        for (int i = 0; i < pontosEntrada.size(); i++) {
                            cadastrarPontosnoBD(idRota, pontosEntrada.get(i).latitude, pontosEntrada.get(i).longitude, i + 1);
                        }
                        progressDialog.cancel();
                        ultimaRotaAdd();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCadastroRota.this);
                        builder.setMessage("Erro ao cadastrar usuário")
                                .setNegativeButton("Tentar novamente", null)
                                .create()
                                .show();
                    }\

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        FinalizarCadastroRotaRequest finalizarCadastroRotaRequest = new FinalizarCadastroRotaRequest("Rota sem Nome", usuarioLogado.getUsuario(), rota.getOrigemLat().toString(),
                rota.getOrigemLng().toString(), rota.getDestinoLat().toString(), rota.getDestinoLng().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(finalizarCadastroRotaRequest);
    }

    public void cadastrarPontosnoBD(final int codRota, Double latitude, Double longitude, int nroPonto) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Log.v("ENTOU", "EEE");
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
        int codPonto = Integer.parseInt(Integer.toString(codRota).concat(Integer.toString(nroPonto)));
        FinalizarCadastroPontoRequest finalizarCadastroPontoRequest = new FinalizarCadastroPontoRequest(codPonto, codRota, latitude, longitude, nroPonto, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(finalizarCadastroPontoRequest);
    }

    public void ultimaRotaAdd() {
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

        UltimaRotaAddRequest ultimaRotaAddRequest = new UltimaRotaAddRequest(usuarioLogado.getUsuario(), responseListener);
        Log.v("ASSSSSSASSASS", usuarioLogado.getUsuario());
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(ultimaRotaAddRequest);
    }

    public void editarPontos(final int codRota) {
        IDCODROTA = codRota;
        Log.v("VELHO DO CEU", Integer.toString(codRota));
        progressDialog = ProgressDialog.show(FinalizarCadastroRota.this, "Carregando Pontos", "Aguarde");
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
                        pontoItem.add(new PontoItem(jsonResponse.getJSONArray(i).getInt(0), jsonResponse.getJSONArray(i).getInt(4), c.latLngtoAddress(origemLatLng.latitude, origemLatLng.longitude),
                                c.latLngtoAddress(destinoLatLng.latitude, destinoLatLng.longitude), codRota));
                    }
                    list(pontoItem);
                    progressDialog.cancel();
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

    public void list(ArrayList<PontoItem> agenda) {
        Log.v("asnkjasj", "FILHA DE UMA PUTA");
        PontoItemAdapter adapter = new PontoItemAdapter(this, agenda);
        listView = (ListView) findViewById(R.id.listViewdePontos);
        listView.setAdapter(adapter);

    }

    public void botaoFinalizarCadastroRotaePontos(View v) {
        Log.v("asjdasjkld", Integer.toString(IDCODROTA));
        EditText eTNomeRota = (EditText) findViewById(R.id.nomeRota);
        EditText eTDescricao = (EditText) findViewById(R.id.descricaoRota);
        String nomeRota = eTNomeRota.getText().toString();
        String descricao = eTDescricao.getText().toString();
        progressDialog = ProgressDialog.show(FinalizarCadastroRota.this, "Finalizando Rota", "Aguarde");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), Navegacao.class);
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

        UpdateRotaRequest updateRotaRequest = new UpdateRotaRequest(IDCODROTA, nomeRota, descricao, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinalizarCadastroRota.this);
        queue.add(updateRotaRequest);

    }

}
*/