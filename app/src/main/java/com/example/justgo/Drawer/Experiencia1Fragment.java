package com.example.justgo.Drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justgo.CadastroRota.PontoItemAdapter;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;


/**
 * Created by Larys on 17/08/2017.
 */

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Requests.GetPontoRequest;
import com.example.justgo.Requests.GetRotasdoUsuarioRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.justgo.LogineCadastro.LoginActivity.usuarioLogado;


public class Experiencia1Fragment extends Fragment {
    ArrayList<RotaItem> rotaItem;
    ListView listView;
    ProgressDialog progressDialog;
    Conversor conversor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experiencias1_fragment, container, false);
         conversor = new Conversor(getContext());
        rotaItem = new ArrayList<RotaItem>();
        rotaItem.clear();
        editarPontos();
        return view;
    }

    public void editarPontos() {
        rotaItem.clear();
        progressDialog = ProgressDialog.show(getContext(), "CarregandoPontos", "Aguarde");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final JSONArray jsonResponse = new JSONArray(response);
                    Log.v("saadsassa", Integer.toString(jsonResponse.length()));

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        String nomeRota = jsonResponse.getJSONArray(i).getString(2);
                        LatLng origemLatLng = new LatLng(jsonResponse.getJSONArray(i).getDouble(3),jsonResponse.getJSONArray(i).getDouble(4));
                        LatLng destinoLatLng = new LatLng(jsonResponse.getJSONArray(i).getDouble(5),jsonResponse.getJSONArray(i).getDouble(6));
                        int codRota = jsonResponse.getJSONArray(i).getInt(0);

                        Address origem = conversor.latLngtoAddress2(origemLatLng.latitude,origemLatLng.longitude);
                        Address destino = conversor.latLngtoAddress2(destinoLatLng.latitude,destinoLatLng.longitude);


                        rotaItem.add(new RotaItem(nomeRota,origem.getSubLocality(),destino.getSubLocality(),codRota));
                    }
                    list(rotaItem);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            Log.v("CLICOU EM: ", Integer.toString(position));
                            Intent intent = new Intent(getActivity(),MostrarExperiencia.class);
                            try{
                                progressDialog.cancel();
                                intent.putExtra("codRota", jsonResponse.getJSONArray(position).getInt(0));
                                startActivity(intent);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            /*Intent intent = new Intent(Experiencia1Fragment.this, EditarPonto.class);
                            try {
                                intent.putExtra("codRota", jsonResponse.getJSONArray(position).getInt(0));
                                intent.putExtra("codPonto", jsonResponse.getJSONArray(position).getInt(3));
                                //continuar = false;
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            //  intent.putExtra("codPonto",);
                        }
                    });
                    progressDialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        GetRotasdoUsuarioRequest getPontoRequest = new GetRotasdoUsuarioRequest(usuarioLogado.getUsuario(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPontoRequest);
    }

    public void list(ArrayList<RotaItem> agenda){
        RotaItemAdapter adapter = new RotaItemAdapter(getActivity(), agenda);
        listView = (ListView) getView().findViewById(R.id.listViewMinhasRotas);
        listView.setAdapter(adapter);
    }

}
