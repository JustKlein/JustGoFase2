package com.example.justgo.CadastroRota;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.R;
import com.example.justgo.Requests.EditarPontoRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class EditarPonto extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private EditText eTDescricao,eTTempo,eTPreco;
    private int codRota, codPonto,nroPonto;
    ImageView iV;
    Spinner spin;
    Spinner combo;
    String meiodeTransporte;
    private static final String[] CLUBES = new String[]{"Carro","Avião","Ônibus","Táxi","Uber","À pé"};
    String[] transportes ={"À pé","Avião","Carro","Metrô","Ônibus","Táxi","Trem","Uber"};
    int [] meioDeTransp = {R.drawable.ic_ape,R.drawable.ic_aviao,R.drawable.ic_carro,R.drawable.ic_metro,
            R.drawable.ic_onibus,R.drawable.ic_taxi,R.drawable.ic_trem,R.drawable.ic_uber};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ponto);
        Bundle bundle = getIntent().getExtras();
        codRota = bundle.getInt("codRota");
        nroPonto = bundle.getInt("nroPonto");
        codPonto = bundle.getInt("codPonto");
    eTDescricao = (EditText) findViewById(R.id.descricaoPonto);
    eTTempo = (EditText) findViewById(R.id.tempoPonto);
    eTPreco = (EditText) findViewById(R.id.precoPonto);
iV= (ImageView) findViewById(R.id.imagem);
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),meioDeTransp,transportes);
        spin.setAdapter(customAdapter);
    }
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {
        meiodeTransporte = transportes[position].toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public void botaoConfirmarEditarPonto(View v){
        String descricao = eTDescricao.getText().toString();
        String tempo = eTTempo.getText().toString();
        String preco = eTPreco.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.v("asdkla","aaaaaaaaaaaaaaaaaaa");
                    if (success) {
                        Log.v("PONTO EDITADO","OKZAO");
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        };

        EditarPontoRequest editarPontoRequest = new EditarPontoRequest(codPonto,codRota,nroPonto,descricao,tempo,preco,meiodeTransporte,responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarPonto.this);
        queue.add(editarPontoRequest);
    }
    public void botaoAdicionarImagemEditarPonto(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Log.v("saas",selectedImageUri.toString());
                iV.setImageURI(selectedImageUri);
            }
        }
    }
    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}

