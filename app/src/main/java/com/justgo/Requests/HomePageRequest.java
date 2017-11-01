package com.justgo.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keven on 9/8/2017.
 */

public class HomePageRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://justgodb.000webhostapp.com/homePage.php";
    private Map<String, String> params;

    public HomePageRequest(Double latitude, Double longitude, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("latitudeEntrada", Double.toString(latitude));
        params.put("longitudeEntrada", Double.toString(longitude));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
