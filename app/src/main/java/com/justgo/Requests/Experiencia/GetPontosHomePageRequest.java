package com.justgo.Requests.Experiencia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/9/2017.
 */

public class GetPontosHomePageRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/Experiencia/getPontosHomePage.php";
    private Map<String, String> params;

    public GetPontosHomePageRequest(String pontos, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("pontosEntrada", pontos);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
