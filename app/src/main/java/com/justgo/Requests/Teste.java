package com.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 10/8/2017.
 */

public class Teste extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/editarPonto.php";
    private Map<String, String> params;

    public Teste(Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
