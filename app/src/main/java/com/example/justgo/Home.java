package com.example.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onClickHomePage(View v){
        Intent homepage =  new Intent(this, HomePageMaps.class);
        startActivity(homepage);
    }
    public void onClickAddRota(View v){
        Intent Address =  new Intent(this, AddressLocationActivity.class);
        startActivity(Address);
    }
}
