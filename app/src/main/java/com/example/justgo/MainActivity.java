package com.example.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickButtonLoginMain(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
    }
    public void onClickButtonCadastroMain(View v){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
