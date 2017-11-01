package com.justgo.Drawer;

/**
 * Created by Keven on 9/8/2017.
 */

public class TrechoItem {
    private int codPonto,codRota, nroPonto;
    private float preco,tempo;
    String origem,destino;
    private String descricao, meiodeTransporte;
    public TrechoItem(int codPonto, String origem, String destino, String descricao, float tempo, float preco, String meiodeTransporte) {
        this.codPonto = codPonto;
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

    public int getNroPonto() {
        return nroPonto;
    }

    public void setNroPonto(int nroPonto) {
        this.nroPonto = nroPonto;
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

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }
}
