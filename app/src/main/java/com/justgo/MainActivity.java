package com.justgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.justgo.LogineCadastro.LoginActivity;
import com.justgo.LogineCadastro.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        String logado = shared.getString("Usuario", "");
        System.out.println(logado);
        if (!logado.isEmpty()) {
            System.out.print("que saco");
            Intent home = new Intent(MainActivity.this, Navegacao.class);
            usuarioLogado.setUsuario(logado);
            startActivity(home);
        }
        else{
            System.out.print("EU NAO SEU FAZR ISSO" );
        }
*/
    }
    public void onClickfazerLoginMainActivity(View v){
        System.out.print("adskajskldaskljasjdskla" );
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
    }
    public void onClickfazerCadastroEmailMainActivity(View v){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
