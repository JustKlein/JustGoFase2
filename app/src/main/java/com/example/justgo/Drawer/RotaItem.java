package com.example.justgo.Drawer;

/**
 * Created by Keven on 9/5/2017.
 */

public class RotaItem {

    private String nomeRota;
    private int codRota;

    public RotaItem(String nomeRota, int codRota) {
        this.nomeRota = nomeRota;
        this.codRota = codRota;
    }

    public String getNomeRota() {
        return nomeRota;
    }

    public void setNomeRota(String nomeRota) {
        this.nomeRota = nomeRota;
    }

    public int getCodRota() {
        return codRota;
    }

    public void setCodRota(int codRota) {
        this.codRota = codRota;
    }



}
