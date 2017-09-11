package com.example.justgo.Drawer;

/**
 * Created by Keven on 9/5/2017.
 */

public class RotaItem {

    private String origem,destino,nomeRota;
    private int codRota;

    public RotaItem(String nomeRota,String origem, String destino, int codRota) {
        this.nomeRota = nomeRota;
        this.origem = origem;
        this.destino = destino;
        this.codRota = codRota;
    }

    public String getNomeRota() {
        return nomeRota;
    }

    public void setNomeRota(String nomeRota) {
        this.nomeRota = nomeRota;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCodRota() {
        return codRota;
    }

    public void setCodRota(int codRota) {
        this.codRota = codRota;
    }



}
