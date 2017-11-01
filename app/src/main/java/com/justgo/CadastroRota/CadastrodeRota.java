package com.justgo.CadastroRota;

import android.app.AlertDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.places.Place;
import com.justgo.Requests.CadastrarRotaRequest;
import com.justgo.Requests.UpdateRotaRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Keven on 9/27/2017.
 */

public class CadastrodeRota {
    private ArrayList<String> nomes;
    private ArrayList<Double> latitudes;
    private ArrayList<Double> longitudes;
    private ArrayList<String> placeIDs;
    private ArrayList<String> placesNames;
    private ArrayList<String> placesAdress;



    public void cadastrarnaTabelaRota(String email, ArrayList<Place> places, Context context){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idRota = -1;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    android.util.Log.v("ENTROU","ENTROU AQUI");
                    idRota  = jsonResponse.getInt("idRota");
                    meAjudaDeus(idRota);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String origemLat = Double.toString(places.get(0).getLatLng().latitude);
        String origemLng = Double.toString(places.get(0).getLatLng().longitude);
        String destinoLat = Double.toString(places.get(places.size()-1).getLatLng().latitude);
        String detinoLng = Double.toString(places.get(places.size()-1).getLatLng().longitude);
        String enderecoOrigem = (places.get(0).getAddress()).toString();
        String enderecoDestino = (places.get(places.size()-1).getAddress()).toString();
        String placeIDOrigem = places.get(0).getId();
        String placeIDDestino = (places.get(places.size()-1).getId());

        pontostoArray(places);
        CadastrarRotaRequest finalizarCadastroRotaRequest = new CadastrarRotaRequest("Rota sem Nome", email,origemLat,origemLng,destinoLat,detinoLng,enderecoOrigem,enderecoDestino,placeIDOrigem,placeIDDestino,converterArrayDoubletoString(latitudes),converterArrayDoubletoString(longitudes),converterArrayStringtoString(placeIDs),converterArrayStringtoString(placesNames),converterArrayStringtoString(placesAdress),responseListener);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(finalizarCadastroRotaRequest);
    }
    public void pontostoArray(ArrayList<Place> places){
        nomes = new ArrayList<String>();
        latitudes = new ArrayList<Double>();
        longitudes = new ArrayList<Double>();
        placeIDs = new ArrayList<String>();
        placesNames = new ArrayList<String>();
        placesAdress = new ArrayList<String>();
        for(int i =0;i<places.size();i++){
            latitudes.add(places.get(i).getLatLng().latitude);
            longitudes.add(places.get(i).getLatLng().longitude);
            placeIDs.add(places.get(i).getId());
            placesNames.add(places.get(i).getName().toString());
            placesAdress.add(places.get(i).getAddress().toString());

        }
    }
    public String converterArrayStringtoString(ArrayList<String> array) {
        String pontos = "";

        for (int i = 0; i < array.size(); i++) {
            pontos = pontos.concat(array.get(i).toString());
            if (i != array.size() - 1)
                pontos = pontos.concat(":");
        }
        android.util.Log.v("aslads", pontos);
        return pontos;
    }
    public String converterArrayDoubletoString(ArrayList<Double> array) {
        String pontos = "";

        for (int i = 0; i < array.size(); i++) {
            pontos = pontos.concat(array.get(i).toString());
            if (i != array.size() - 1)
                pontos = pontos.concat(":");
        }
        android.util.Log.v("aslads", pontos);
        return pontos;
    }
    public void meAjudaDeus(int vai ){
        RotaSingleton rotaSingleton= RotaSingleton.getInstancia();
        rotaSingleton.setCodRota(vai);
        android.util.Log.v("VAI", Integer.toString(rotaSingleton.getCodRota()));
    }

    public void finalizarCadastroRota(final Context context, String nomeRota, String descricao){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        RotaSingleton rotaSingleton = RotaSingleton.getInstancia();
        UpdateRotaRequest updateRotaRequest = new UpdateRotaRequest(rotaSingleton.getCodRota(), nomeRota, descricao, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(updateRotaRequest);
}
}
