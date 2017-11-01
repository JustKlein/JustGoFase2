package com.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/23/2017.
 */

public class CadastrarRotaRequest extends StringRequest{

        private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/cadastrarRota.php";
        private Map<String, String> params;

        public CadastrarRotaRequest(String nome, String email, String origemLatitude,String origemLongitude,
                                            String destinoLatitude, String destinoLongitude,String enderecoOrigem,String enderecoDestino, String placeIDOrigem,String placeIDDestino,String latitudes, String longitudes, String placesIDs,String placesNames, String placesAddress, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("emailUsuarioEntrada",email);
            params.put("nomeRotaEntrada", nome);
            params.put("origemLatEntrada", origemLatitude);
            params.put("origemLngEntrada", origemLongitude);
            params.put("destinoLatEntrada", destinoLatitude);
            params.put("destinoLngEntrada", destinoLongitude);
            params.put("enderecoOrigem",enderecoOrigem);
            params.put("enderecoDestino",enderecoDestino);
            params.put("placeIDOrigem",placeIDOrigem);
            params.put("placeIDDestino",placeIDDestino);
            params.put("latitudes",latitudes);
            params.put("longitudes",longitudes);
            params.put("placesIDS",placesIDs);
            params.put("placesNames",placesNames);
            params.put("placesAddress",placesAddress);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
}
