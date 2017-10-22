package com.example.justgo.LogineCadastro;

/**
 * Created by Keven on 8/13/2017.
 */

public class UsuarioLogadoSingleton {
    protected String email;
    protected String nome;
    public static UsuarioLogadoSingleton instancia;
    protected UsuarioLogadoSingleton(){

    }
    public static UsuarioLogadoSingleton getInstancia(){
        if(instancia ==null)
            instancia = new UsuarioLogadoSingleton();
        return instancia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
