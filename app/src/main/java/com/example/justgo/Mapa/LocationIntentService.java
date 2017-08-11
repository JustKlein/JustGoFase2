
package com.example.justgo.Mapa;


import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by viniciusthiengo on 3/1/15.
 */
public class LocationIntentService extends IntentService {


    public LocationIntentService() {
        super("worker_thread");
        Log.v("ENTROU", "ENTROUNOCONSTUTOR");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Location location = intent.getParcelableExtra(AddressLocationActivity.LOCATION);
        int type = intent.getIntExtra(AddressLocationActivity.TYPE, 1);
        String address = intent.getStringExtra(AddressLocationActivity.ADDRESS);
        Log.v("ENTROU", "ENTROUNESSE TREM AQUI");
        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String error = "";
        String resultAddress = "";

        String resultAddress2 = "";
        try {
            if (type == 2 || address == null) {
                list = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            } else {
                list = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            error = "Network problem";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            error = "Illegal arguments";
        }


        if (list != null && list.size() > 0) {
            Address a = list.get(0);
            resultAddress += a.getLatitude();
            resultAddress2 += a.getLongitude();
        } else {
            resultAddress = error;
          //  resultAddress2 = 0.0;
        }

        if (resultAddress != error ) {
            Log.v("FOI","FOI");
            MessageEB m = new MessageEB();
            m.setResultLatitude(Double.parseDouble(resultAddress));
            m.setResultLongitude(Double.parseDouble(resultAddress2));
            EventBus.getDefault().post(m);

        }
    }
}
