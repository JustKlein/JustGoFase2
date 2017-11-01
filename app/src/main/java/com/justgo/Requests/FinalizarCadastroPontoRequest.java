package com.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.justgo.teste;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 8/23/2017.
 */

public class FinalizarCadastroPontoRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/ponto.php";
    private Map<String,String> params;
    public static teste t;
    public FinalizarCadastroPontoRequest(int codPonto,int codRota,Double latitude, Double longitude, int nroPonto, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codPontoEntrada", Integer.toString(codPonto));
        params.put("codRotaEntrada", Integer.toString(codRota));
        params.put("latitudeEntrada", Double.toString(latitude));
        params.put("longitudeEntrada", Double.toString(longitude));
        params.put("nroPontoEntrada", Integer.toString(nroPonto));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
