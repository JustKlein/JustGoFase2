package com.justgo.Drawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.justgo.LogineCadastro.UsuarioLogadoSingleton;
import com.justgo.Mapa.Conversor;
import com.justgo.R;
import com.justgo.Requests.GetRotasdoUsuarioRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Larys on 17/08/2017.
 */


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
        progressDialog = ProgressDialog.show(getContext(), "CarregandoPontos", "Aguarde");
        editarPontos();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        editarPontos();
    }

    public void editarPontos() {
        rotaItem.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final JSONArray jsonResponse = new JSONArray(response);
                    Log.v("saadsassa", Integer.toString(jsonResponse.length()));

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        String nomeRota = jsonResponse.getJSONArray(i).getString(2);
                        int codRota = jsonResponse.getJSONArray(i).getInt(0);
                        rotaItem.add(new RotaItem(nomeRota,codRota));
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
                        }
                    });
                    progressDialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        UsuarioLogadoSingleton usuarioLogado= UsuarioLogadoSingleton.getInstancia();
        GetRotasdoUsuarioRequest getPontoRequest = new GetRotasdoUsuarioRequest(usuarioLogado.getEmail(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getPontoRequest);
    }

    public void list(ArrayList<RotaItem> agenda){
        RotaItemAdapter adapter = new RotaItemAdapter(getActivity(), agenda);
        listView = (ListView) getView().findViewById(R.id.listViewMinhasRotas);
        listView.setAdapter(adapter);
    }

}
