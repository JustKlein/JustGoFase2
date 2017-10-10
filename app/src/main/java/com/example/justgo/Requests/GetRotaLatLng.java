package com.example.justgo.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 10/8/2017.
 */

public class GetRotaLatLng extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://justgodb.000webhostapp.com/getRotaLatLng.php";
    private Map<String, String> params;

    public GetRotaLatLng(String latLng, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("latlng", latLng);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
