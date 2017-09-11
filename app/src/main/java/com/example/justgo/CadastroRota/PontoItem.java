package com.example.justgo.CadastroRota;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Keven on 8/26/2017.
 */

public class PontoItem {
    private String origem,destino;
    private int nroPonto,codRota,codPonto;

    public int getCodPonto() {
        return codPonto;
    }

    public void setCodPonto(int codPonto) {
        this.codPonto = codPonto;
    }

    public PontoItem(int codPonto, int nroPonto, String origem, String destino, int codRota) {
        this.origem = origem;
        this.destino = destino;
        this.nroPonto = nroPonto;
        this.codRota = codRota;
        this.codPonto = codPonto;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String  origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getNroPonto() {
        return nroPonto;
    }

    public void setNroPonto(int codPonto) {
        this.nroPonto = nroPonto;
    }

    public int getCodRota() {
        return codRota;
    }

    public void setCodRota(int codRota) {
        this.codRota = codRota;
    }


}
