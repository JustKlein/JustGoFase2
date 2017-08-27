package com.example.justgo.CadastroRota;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Larys on 17/08/2017.
 */

public class RotaAux {
    public static Double origemLat,origemLng,destinoLat,destinoLng;
    private static List<LatLng> pontos;
    private static int codRota;

    public static int getCodRota() {
        return codRota;
    }

    public static void setCodRota(int codRota) {
        RotaAux.codRota = codRota;
    }

    public static List<LatLng> getPontos() {
        return pontos;
    }

    public static void setPontos(List<LatLng> pontos) {
        RotaAux.pontos = pontos;
    }

    private static List<Marker> markers;


    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }



    public static Double getDestinoLng() {
        return destinoLng;
    }

    public static void setDestinoLng(Double destinoLng) {
        RotaAux.destinoLng = destinoLng;
    }

    public static Double getOrigemLng() {
        return origemLng;
    }

    public static void setOrigemLng(Double origemLng) {
        RotaAux.origemLng = origemLng;
    }

    public static Double getDestinoLat() {
        return destinoLat;
    }

    public static void setDestinoLat(Double destinoLat) {
        RotaAux.destinoLat = destinoLat;
    }

    public static Double getOrigemLat() {
        return origemLat;
    }

    public static void setOrigemLat(Double origemLat) {
        RotaAux.origemLat = origemLat;
    }
}
