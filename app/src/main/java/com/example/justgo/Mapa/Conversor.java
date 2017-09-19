package com.example.justgo.Mapa;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
    private boolean teste = false;
    private Geocoder geocoder;
    private List<Address> list;
    private String error = "",resultAddress="",resultAddress2="";
    public Conversor(Context c){
        list = new ArrayList<Address>();
        geocoder = new android.location.Geocoder(c, Locale.getDefault());
    }
    public String latLngtoAddress(Double latitude, Double longitude){
        resultAddress = "";
        resultAddress2="";
        Address a = null;
        Location location = new Location("oi");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        try {
            list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            a = list.get(0);
            for(int i = 0, tam = a.getMaxAddressLineIndex(); i < tam; i++){
                resultAddress += a.getAddressLine(i);
                resultAddress += i < tam - 1 ? ", " : "";
                Log.v("o","oi");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            error = "Illegal arguments";
        }
        Log.v("entrou",a.getAddressLine(0));
        return a.getAddressLine(0);
    }

    public Address latLngtoAddress2(Double latitude, Double longitude){
        resultAddress = "";
        resultAddress2="";
        Address a = null;
        Location location = new Location("oi");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        try {
            list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            a = list.get(0);
            for(int i = 0, tam = a.getMaxAddressLineIndex(); i < tam; i++){
                resultAddress += a.getAddressLine(i);
                resultAddress += i < tam - 1 ? ", " : "";
                Log.v("o","oi");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            error = "Illegal arguments";
        }
        Log.v("entrou",a.getAddressLine(0));
        return a;
    }
    public Double[] addressToLatLng(String address){
        teste = true;
        Double[] array = new Double[2];
        resultAddress = "";
        resultAddress2="";
        do {
            try {
                list = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
                teste =false;
            } catch (IOException e) {
                teste = true;
                e.printStackTrace();
                error = "Network problem";
            } catch (IllegalArgumentException e) {
                teste=true;
                e.printStackTrace();
                error = "Illegal arguments";
            }
            if(list == null || !(list.size()>0)){
                teste = true;
            }
        }while(teste);
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
        Log.v("askjaskls",resultAddress2);
        return array;
    }
}
