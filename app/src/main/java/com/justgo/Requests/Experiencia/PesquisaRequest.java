package com.justgo.Requests.Experiencia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/10/2017.
 */

public class PesquisaRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/Experiencia/pesquisa.php";
    private Map<String, String> params;

    public PesquisaRequest(String pesquisa, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("pesquisaEntrada", pesquisa);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
