package com.example.justgo.Drawer;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Keven on 9/8/2017.
 */

public class TrechoItem {
    private int codPonto,codRota, nroPonto;
    private double preco;
    LatLng origem,destino;
    private String descricao, meiodeTransporte,tempo;
    public TrechoItem(int codPonto, int codRota, LatLng origem, LatLng destino, int nroPonto, String descricao, String tempo, double preco, String meiodeTransporte) {
        this.codPonto = codPonto;
        this.codRota = codRota;
        this.nroPonto = nroPonto;
        this.origem = origem;
        this.destino = destino;
        this.tempo = tempo;
        this.preco = preco;
        this.descricao = descricao;
        this.meiodeTransporte = meiodeTransporte;
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

    public int getNroPonto() {
        return nroPonto;
    }

    public void setNroPonto(int nroPonto) {
        this.nroPonto = nroPonto;
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

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMeiodeTransporte() {
        return meiodeTransporte;
    }

    public void setMeiodeTransporte(String meiodeTransporte) {
        this.meiodeTransporte = meiodeTransporte;
    }


}
