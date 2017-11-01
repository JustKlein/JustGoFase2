package com.justgo.CadastroRota;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.justgo.R;
import com.justgo.Requests.EditarPontoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditarPonto extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ProgressDialog progressDialog;
    private EditText eTDescricao,eTTempo,eTPreco;
    private int codRota, codPonto,nroPonto,position;
    ImageView iV;
    Spinner spin;
    Spinner combo;
    String meiodeTransporte;
    String origem, destino;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imagefoto;
    private static final String[] CLUBES = new String[]{"Carro","Avião","Ônibus","Táxi","Uber","À pé"};
    String[] transportes ={"À pé","Avião","Carro","Metrô","Ônibus","Táxi","Trem","Uber"};
    int [] meioDeTransp = {R.drawable.ic_ape,R.drawable.ic_aviao,R.drawable.ic_carro,R.drawable.ic_metro,
            R.drawable.ic_onibus,R.drawable.ic_taxi,R.drawable.ic_trem,R.drawable.ic_uber};
    Bitmap bitmap;
    RotaSingleton rotaSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ponto);
        Bundle bundle = getIntent().getExtras();
        codPonto = bundle.getInt("codPonto");
        origem =  bundle.getString("origem");
    destino = bundle.getString("destino");
        position = bundle.getInt("posicao");
    eTDescricao = (EditText) findViewById(R.id.descricaoPonto);
    eTTempo = (EditText) findViewById(R.id.tempoPonto);
    eTPreco = (EditText) findViewById(R.id.precoPonto);
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        imagefoto = (ImageView) findViewById(R.id.imgfoto);
rotaSingleton = RotaSingleton.getInstancia();
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        /*Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        imagefoto.setImageBitmap(bitmap);*/
        return encodedImage;
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
                    if (success) {
                        Intent it = new Intent();
                        it.putExtra("Resposta",position);
                        setResult(RESULT_OK,it);
                        EditarPonto.super.onBackPressed();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditarPonto.this);
                        builder.setMessage("Erro ao cadastrar usuário")
                                .setNegativeButton("Tentar novamente", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        EditarPontoRequest teste = new EditarPontoRequest(codPonto,rotaSingleton.getCodRota(),origem,destino,descricao,tempo,preco,meiodeTransporte,responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarPonto.this);
        queue.add(teste);
       /* String imagem = getStringImage(bitmap);
        Log.v("IMAMGE", imagem);
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        };

        ImagensRequest imagemRequest = new ImagensRequest(codPonto,imagem,responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarPonto.this);
        queue.add(imagemRequest);
*/



/*
        String descricao = eTDescricao.getText().toString();
        String tempo = eTTempo.getText().toString();
        String preco = eTPreco.getText().toString();
       // progressDialog = ProgressDialog.show(EditarPonto.this, "Salvando", "Aguarde");
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("EEAEE","antes do try");
                try {
                    Log.v("EEAEE","dentro do try");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    Log.v("bool",Boolean.toString(success));
                    if (success) {
                       // progressDialog.cancel();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    Log.v("EEAEE","deu erro");
                    e.printStackTrace();

                }
            }
        };
        Log.v("EEAEE","hey1");
        EditarPontoRequest editarPontoRequest = new EditarPontoRequest(codPonto,origem,destino,descricao,tempo,preco,meiodeTransporte,responseListener);
        Log.v("EEAEE","hey2");
        RequestQueue queue = Volley.newRequestQueue(EditarPonto.this);
        Log.v("EEAEE","hey3");
        queue.add(editarPontoRequest);
        Log.v("EEAEE","hey4");
*/

    }


    public void botaoAdicionarImagemEditarPonto(View v){
        showFileChooser();

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
               // imagefoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

