package com.example.justgo.CadastroRota;

import com.example.justgo.logger.Log;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

/**
 * Created by Keven on 9/27/2017.
 */

public class RotaSingleton {
    private  int codRota;
    protected String email;
    protected ArrayList<Place> places;
    public static RotaSingleton instancia;

    protected RotaSingleton(){

    }
    public static RotaSingleton getInstancia(){
        if(instancia ==null) {
            instancia = new RotaSingleton();
            android.util.Log.v("INSTANICA", "PRIMERIA CRCAOAC");
        }
        else{
            android.util.Log.v("INSTANICA", "JA FOI CRIADA");
        }
        return instancia;

    }

    public  int getCodRota() {
        return codRota;
    }

    public  void setCodRota(int codRota) {
        this.codRota = codRota;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}
