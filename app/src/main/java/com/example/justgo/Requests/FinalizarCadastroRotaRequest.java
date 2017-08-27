package com.example.justgo.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/23/2017.
 */

public class FinalizarCadastroRotaRequest extends StringRequest{

        private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/rota.php";
        private Map<String, String> params;

        public FinalizarCadastroRotaRequest(String nome, String email, String origemLatitude,String origemLongitude,
                                            String destinoLatitude, String destinoLongitude, Response.Listener<String> listener) {
            super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("emailUsuarioEntrada",email);
            params.put("nomeRotaEntrada", nome);
            params.put("origemLatEntrada", origemLatitude);
            params.put("origemLngEntrada", origemLongitude);
            params.put("destinoLatEntrada", destinoLatitude);
            params.put("destinoLngEntrada", destinoLongitude);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
}
