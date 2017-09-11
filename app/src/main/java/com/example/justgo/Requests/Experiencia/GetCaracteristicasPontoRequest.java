package com.example.justgo.Requests.Experiencia;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/7/2017.
 */

public class GetCaracteristicasPontoRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/Experiencia/getCaracteristicasPonto.php";
    private Map<String, String> params;

    public GetCaracteristicasPontoRequest(int codRota, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codRotaEntrada", Integer.toString(codRota));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
