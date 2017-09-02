package com.example.justgo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.LogineCadastro.RegisterActivity;
import com.example.justgo.Requests.EditarPontoRequest;
import com.example.justgo.Requests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class EditarPonto extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText eTDescricao;
    private int codRota, codPonto;
    Spinner combo;
    String oi;
    private static final String[] CLUBES = new String[]{"Carro","Avião","Ônibus","Táxi","Uber","À pé"};
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ponto);
        Bundle bundle = getIntent().getExtras();
        codRota = bundle.getInt("codRota");
        codPonto = bundle.getInt("codPonto");
        eTDescricao = (EditText) findViewById(R.id.descricaoPonto);
        combo = (Spinner) findViewById(R.id.meiosdeTransporte);
    ArrayAdapter adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CLUBES);
    adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
    combo.setAdapter(adp);
    combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            oi = parent.getItemAtPosition(position).toString();
            Log.v("oi",oi);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    }
    @Override
    public void onBackPressed(){
        String descricao = eTDescricao.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Log.v("PONTO EDITADO",oi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        EditarPontoRequest editarPontoRequest = new EditarPontoRequest(codRota,codPonto,descricao,responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarPonto.this);
        queue.add(editarPontoRequest);
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
