package com.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 10/6/2017.
 */

public class ImagensRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/imagens.php";
    private Map<String, String> params;

    public ImagensRequest(int codPonto, String image, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codPontoEntrada", Integer.toString(codPonto));
        params.put("imagem",image);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
