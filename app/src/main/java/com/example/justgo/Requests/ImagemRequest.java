package com.example.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/7/2017.
 */

public class ImagemRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://justgodb.000webhostapp.com/imagem.php";
    private Map<String, String> params;

    public ImagemRequest(String CodRota,int CodPonto, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("emailEntrada", CodRota);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
