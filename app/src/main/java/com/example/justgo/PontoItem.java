package com.example.justgo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Keven on 8/26/2017.
 */

public class PontoItem {
    private LatLng origem,destino;
    private int codPonto,codRota;

    public PontoItem(int codPonto,LatLng origem, LatLng destino,  int codRota) {
        this.origem = origem;
        this.destino = destino;
        this.codPonto = codPonto;
        this.codRota = codRota;
    }

    public LatLng getOrigem() {
        return origem;
    }

    public void setOrigem(LatLng origem) {
        this.origem = origem;
    }

    public LatLng getDestino() {
        return destino;
    }

    public void setDestino(LatLng destino) {
        this.destino = destino;
    }

    public int getCodPonto() {
        return codPonto;
    }

    public void setCodPonto(int codPonto) {
        this.codPonto = codPonto;
    }

    public int getCodRota() {
        return codRota;
    }

    public void setCodRota(int codRota) {
        this.codRota = codRota;
    }


}
