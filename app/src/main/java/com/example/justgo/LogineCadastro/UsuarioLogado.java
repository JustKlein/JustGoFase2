package com.example.justgo.LogineCadastro;

/**
 * Created by Keven on 8/13/2017.
 */

public class UsuarioLogado {
    public static String usuariocod;

    public void setUsuario(String email){
        usuariocod = email;
    }
    public String getUsuario(){
        return usuariocod;
    }
}
