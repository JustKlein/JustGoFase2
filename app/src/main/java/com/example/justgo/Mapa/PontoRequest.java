package com.example.justgo.Mapa;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/11/2017.
 */

public class PontoRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/ponto.php";
    private Map<String, String> params;

    public PontoRequest(Double latitude, Double longitude, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("latitudeEntrada", latitude.toString());
        params.put("longitudeEntrada", longitude.toString());
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}