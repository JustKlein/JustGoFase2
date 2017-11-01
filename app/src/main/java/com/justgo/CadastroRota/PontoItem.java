package com.justgo.CadastroRota;

/**
 * Created by Keven on 8/26/2017.
 */

public class PontoItem {
    private String origem,destino;
    private int codPonto;
    private boolean foiAdicionado;

    public int getCodPonto() {
        return codPonto;
    }

    public void setCodPonto(int codPonto) {
        this.codPonto = codPonto;
    }

    public PontoItem(int codPonto, String origem, String destino) {
        this.origem = origem;
        this.destino = destino;
        this.codPonto = codPonto;
    }

    public boolean isFoiAdicionado() {
        return foiAdicionado;
    }

    public void setFoiAdicionado(boolean foiAdicionado) {
        this.foiAdicionado = foiAdicionado;
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

}
