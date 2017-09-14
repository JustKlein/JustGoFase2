package com.example.justgo.Drawer;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.example.justgo.R;

public class Experiencias extends AppCompatActivity {
//TESTE PQ SO JESUS SABE COMO FAZER ISSO FUNCIONARS
    private static final String TAG = "Experencias";

    private Secoes secoes;
//lallallall
    //testeee
    //COLEZEEEE
    //colezze
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);
        Log.d(TAG,"onCreate: Starting");

        secoes = new Secoes(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager)
    {
        Secoes adaptador = new Secoes(getSupportFragmentManager());
        adaptador.adicionaFragmento(new Experiencia1Fragment(), "Minhas Experiencias");
        adaptador.adicionaFragmento(new Experiencia2Fragment(),"Pesquisar Experiências");
        adaptador.adicionaFragmento(new Experiencia3Fragment(),"Sugestões de Lugares");
        viewPager.setAdapter(adaptador);
    }
}
