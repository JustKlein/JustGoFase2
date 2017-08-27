package com.example.justgo.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.justgo.teste;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Keven on 8/23/2017.
 */

public class FinalizarCadastroPontoRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://justgodb.000webhostapp.com/ponto.php";
    private Map<String,String> params;
    public static teste t;
    public FinalizarCadastroPontoRequest(int codRota,Double latitude, Double longitude, int codPonto, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("codRotaEntrada", Integer.toString(codRota));
        params.put("latitudeEntrada", Double.toString(latitude));
        params.put("longitudeEntrada", Double.toString(longitude));
        params.put("codPontoEntrada", Integer.toString(codPonto));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
