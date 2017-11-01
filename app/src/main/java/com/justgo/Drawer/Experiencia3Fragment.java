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
import com.google.android.gms.maps.model.PolylineOptions;
import com.justgo.Mapa.Conversor;
import com.justgo.R;
import com.justgo.Requests.Experiencia.GetSugestoesRota;
import com.justgo.Requests.HomePageRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by Larys on 17/08/2017.
 */

public class Experiencia3Fragment extends Fragment {
    ArrayList<RotaItem> rotaItem;
    ProgressDialog progressDialog;
    ArrayList pontosDentrodoRaio;
    ListView listView;
    Conversor conversor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experiencias3_fragment, container, false);
        rotaItem = new ArrayList<RotaItem>();
        rotaItem.clear();
        pontosDentrodoRaio = new ArrayList<Integer>();
        conversor = new Conversor(getContext());
        sugestoes();
        return view;

    }

    public void list(ArrayList<RotaItem> agenda) {
        RotaItemAdapter adapter = new RotaItemAdapter(getActivity(), agenda);
        listView = (ListView) getView().findViewById(R.id.listViewSugestoesRota);
        listView.setAdapter(adapter);
    }
    public void sugestoes() {
        rotaItem.clear();
        progressDialog = ProgressDialog.show(getContext(), "CarregandoPontos", "Aguarde");
        System.out.println("Entrou 1");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    System.out.println("Entrou 1");
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        pontosDentrodoRaio.add(new Integer(jsonResponse.getJSONArray(i).getInt(0)));
                    }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                PolylineOptions rectOptions;
                                System.out.println("Entrou 1");
                                JSONArray jsonResponse = new JSONArray(response);
                                for (int i = 0; i < jsonResponse.length(); i++) {
                                    rectOptions = new PolylineOptions();
                                    System.out.println(jsonResponse.getJSONArray(i));
                                    final JSONArray json = jsonResponse.getJSONArray(i);
                                    System.out.println(json.length());
                                    for (int j = 0; j < json.length(); j++) {
                                        int CodRota =json.getJSONArray(j).getInt(0);
                                        String nomeRota = json.getJSONArray(j).getString(1);
                                        System.out.println(nomeRota);
                                        rotaItem.add(new RotaItem(nomeRota,CodRota));
                                    }
                                    progressDialog.cancel();
                                }
                                list(rotaItem);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                        Log.v("CLICOU EM: ", Integer.toString(position));
                                        Intent intent = new Intent(getActivity(),MostrarExperiencia.class);
                                            progressDialog.cancel();
                                            intent.putExtra("codRota",rotaItem.get(position).getCodRota());
                                            startActivity(intent);
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                     GetSugestoesRota getSugestoesRota = new GetSugestoesRota(converterArraytoString(pontosDentrodoRaio), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(getSugestoesRota);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HomePageRequest homePageRequest = new HomePageRequest(-19.8986831, -44.0293941, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(homePageRequest);
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
}
