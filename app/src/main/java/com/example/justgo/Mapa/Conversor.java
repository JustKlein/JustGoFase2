package com.example.justgo.Mapa;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;
import static com.example.justgo.R.id.origem;

/**
 * Created by Keven on 8/16/2017.
 */

public class Conversor {
    private Geocoder geocoder;
    private List<Address> list;
    private String error = "",resultAddress="",resultAddress2="";
    public Conversor(Context c){
        list = new ArrayList<Address>();
        geocoder = new android.location.Geocoder(c, Locale.getDefault());
    }

    public Double[] addressToLatLng(String address){
        Double[] array = new Double[2];
        resultAddress = "";
        resultAddress2="";
        try {
            list = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
            error = "Network problem";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            error = "Illegal arguments";
        }
        if (list != null && list.size() > 0) {
            Address a = list.get(0);
            resultAddress += a.getLatitude();
            resultAddress2 += a.getLongitude();
        } else {
            resultAddress = error;
            //  resultAddress2 = 0.0;
        }
        array[0] = Double.parseDouble(resultAddress);
        array[1] = Double.parseDouble(resultAddress2);
Log.v("askjaskls",resultAddress);
        return array;
    }
}
