package com.example.justgo.Mapa;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.justgo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;


public class AddressLocationActivity extends ActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String LOCATION = "location";
    public static final String TYPE = "type";
    public static final String ADDRESS = "address";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private EditText etAddress;
    private TextView tvAddressLocation;
    private Button btNameToCoord;
    private Button btCoordToName;
    private double a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_location);

        //EventBus.getDefault().register(this);
        EventBus.getDefault().register(this);
        etAddress = (EditText) findViewById(R.id.et_address);
        tvAddressLocation = (TextView) findViewById(R.id.tv_address_location);
        btNameToCoord = (Button) findViewById(R.id.bt_name_to_coord);
        btCoordToName = (Button) findViewById(R.id.bt_coord_to_name);

        callConnection();
    }


    private synchronized void callConnection() {
        Log.i("LOG", "AddressLocationActivity.callConnection()");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    public void callIntentService(int type, String address) {
        Intent it = new Intent(this, LocationIntentService.class);
        it.putExtra(TYPE, type);
        it.putExtra(ADDRESS, address);
        it.putExtra(LOCATION, mLastLocation);
        startService(it);
    }


    // LISTERNERS
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("LOG", "AddressLocationActivity.onConnected(" + bundle + ")");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (l != null) {
            mLastLocation = l;
            btNameToCoord.setEnabled(true);
            btCoordToName.setEnabled(true);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "AddressLocationActivity.onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LOG", "AddressLocationActivity.onConnectionFailed(" + connectionResult + ")");
    }

    public void getLocationListener(View view) {
        int type;
        String address = null;

        if (view.getId() == R.id.bt_name_to_coord) {
            type = 1;
            address = etAddress.getText().toString();
        } else {
            type = 2;
        }

        callIntentService(1, address);
    }

    public void onEvent(final MessageEB m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //
                // Log.i("aaaaaaaaaaaaaaaaaaa", Double.toString(m.getResultLatitude()));
                tvAddressLocation.setText("Data: " + m.getResultLatitude() + m.getResultLongitude());
                BD(m.getResultLatitude(), m.getResultLongitude());
            }
        });
    }

    public void BD(Double latitude, Double longitude) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddressLocationActivity.this);
                        builder.setMessage("Usuário cadastrado com sucesso")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddressLocationActivity.this);
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

        PontoRequest pontoRequest = new PontoRequest(latitude,longitude, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddressLocationActivity.this);
        queue.add(pontoRequest);
    }
}
