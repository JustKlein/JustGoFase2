package com.example.justgo.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/28/2017.
 */

public class EditarPontoRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/editarPonto.php";
    private Map<String, String> params;

    public EditarPontoRequest(int codRota, int codPonto,String descricao, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codRotaEntrada", Integer.toString(codRota));
        params.put("codPontoEntrada", Integer.toString(codPonto));
        params.put("descricaoEntrada", descricao);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
