package com.justgo.LogineCadastro;

/**
 * Created by Keven on 8/8/2017.
 */

public class VerificaçãodeDados {
    public String verificarLogin(String email,String senha){
        if(!estaVazio(email).contentEquals("correto") || !estaVazio(senha).contentEquals("correto")
                && validacaoEmail(email).contentEquals("0")){
            return "0";
        }
        else{
            return "Campo Vazio!";
        }
    }
    private String estaVazio(String texto) {
        if (texto.isEmpty())
            return "correto";
        else
            return "campoVazio";
    }
    private String validacaoEmail(String email) {
        if (email.contains("@"))
            return "correto";
        else
            return "Formato de email inválido";

    }
}
