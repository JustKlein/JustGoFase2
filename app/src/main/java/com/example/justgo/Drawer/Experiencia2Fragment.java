package com.example.justgo.Drawer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.Mapa.Conversor;
import com.example.justgo.R;
import com.example.justgo.Requests.Experiencia.PesquisaRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Larys on 17/08/2017.
 */

public class Experiencia2Fragment extends Fragment {
    EditText eTPesquisa;
    Button bTPesquisa;
    Conversor conversor;
    ProgressDialog progressDialog;
    ListView listView;
    ArrayList<RotaItem> rotaItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experiencias2_fragment, container, false);
        eTPesquisa = (EditText) view.findViewById(R.id.campodePesquisa);
        bTPesquisa = (Button) view.findViewById(R.id.botaoPesquisar);
        conversor = new Conversor(getContext());
        rotaItem = new ArrayList<RotaItem>();
        bTPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotaItem.clear();
                pesquisar();
            }
        });
        return view;
    }

  public void pesquisar() {
        String pesquisa = eTPesquisa.getText().toString();
        progressDialog = ProgressDialog.show(getContext(), "Pesquisando", "Aguarde");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONArray jsonResponse = new JSONArray(response);

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        String nomeRota = jsonResponse.getJSONArray(i).getString(1);
                        int codRota = jsonResponse.getJSONArray(i).getInt(0);

                        rotaItem.add(new RotaItem(nomeRota,codRota));
                    }

                    list(rotaItem);
                    progressDialog.cancel();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            Log.v("CLICOU EM: ", Integer.toString(position));
                            Intent intent = new Intent(getActivity(), MostrarExperiencia.class);
                            try {
                                intent.putExtra("codRota", jsonResponse.getJSONArray(position).getInt(0));
                                startActivity(intent);
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

        PesquisaRequest pesquisaRequest = new PesquisaRequest(pesquisa, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(pesquisaRequest);
    }

    public void list(ArrayList<RotaItem> agenda) {
        Log.v("asnkjasj", "FILHA DE UMA PUTA");
        RotaItemAdapter adapter = new RotaItemAdapter(getActivity(), agenda);
        listView = (ListView) getView().findViewById(R.id.listViewRotasPesquisa);
        listView.setAdapter(adapter);
    }
}
