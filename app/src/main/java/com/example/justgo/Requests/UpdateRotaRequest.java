package com.example.justgo.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/6/2017.
 */

 public class UpdateRotaRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://justgodb.000webhostapp.com/updateRota.php";
    private Map<String, String> params;

    public UpdateRotaRequest(int codRota, String nomeRota, String descricao,Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codRotaEntrada", Integer.toString(codRota));
        params.put("nomeRotaEntrada", nomeRota);
        params.put("descricaoEntrada", descricao);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
