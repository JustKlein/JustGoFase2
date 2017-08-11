package com.example.justgo.Mapa;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/11/2017.
 */

public class GetPontoRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://justgodb.000webhostapp.com/getPontoRequest.php";
    private Map<String, String> params;

    public GetPontoRequest(String email, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("emailEntrada", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}