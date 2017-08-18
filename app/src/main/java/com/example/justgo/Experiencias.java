package com.example.justgo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Experiencias extends AppCompatActivity {

    private Secoes secoes;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);

        secoes = new Secoes(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager)
    {
        Secoes adaptador = new Secoes(getSupportFragmentManager());
        adaptador.adicionaFragmento(new Experiencia1Fragment(), "Inserir");
        adaptador.adicionaFragmento(new Experiencia2Fragment(),"Pesquisar");
        adaptador.adicionaFragmento(new Experiencia3Fragment(),"Sugestões");
        viewPager.setAdapter(adaptador);
    }
}
