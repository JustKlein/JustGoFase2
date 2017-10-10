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

    public EditarPontoRequest(int codPonto,int codRota,String origem, String destino, String descricao,String tempo,String preco,String meiodeTransporte, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codPontoEntrada", Integer.toString(codPonto));
        params.put("codRotaEntrada", Integer.toString(codRota));
        params.put("descricaoEntrada", descricao);
        params.put("tempoEntrada", tempo);
        params.put("precoEntrada", preco);
        params.put("meiodeTransporteEntrada", meiodeTransporte);
        params.put("origem",origem);
        params.put("destino",destino);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
