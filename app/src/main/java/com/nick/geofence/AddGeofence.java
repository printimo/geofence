package com.nick.geofence;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.schibstedspain.leku.LocationPickerActivity;

public class AddGeofence extends AppCompatActivity {

    private static final int LOCATION = 2;
    private EditText latitudeText;
    private EditText longitudeText;
    private EditText radius;
    private EditText wifiname;
    private View map;
    private View save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geofence);

        latitudeText = (EditText) findViewById(R.id.add_geofence_latitude);
        longitudeText = (EditText) findViewById(R.id.add_geofence_longitude);
        radius = (EditText) findViewById(R.id.add_geofence_radius);
        wifiname = (EditText) findViewById(R.id.add_geofence_wifi_name);
        map = findViewById(R.id.pick_location_button);
        save = findViewById(R.id.add_geofence_save);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new LocationPickerActivity.Builder()
                        .withLocation(getIntent().getDoubleExtra("lat",0), getIntent().getDoubleExtra("long",0))
                        .withSearchZone("es_ES")
                        .shouldReturnOkOnBackPressed()
                        .withStreetHidden()
                        .withCityHidden()
                        .withZipCodeHidden()
                        .withSatelliteViewHidden()
                        .build(getApplicationContext());

                startActivityForResult(intent, LOCATION);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("long",Double.valueOf(longitudeText.getText().toString()));
                intent.putExtra("lat",Double.valueOf(latitudeText.getText().toString()));
                intent.putExtra("radius", Integer.valueOf(radius.getText().toString()));
                intent.putExtra("wifi",wifiname.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOCATION) {
            if(resultCode == RESULT_OK){
                double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                latitudeText.setText(String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                longitudeText.setText(String.valueOf(longitude));
//                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
//                Log.d("ADDRESS****", String.valueOf(address));
//                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
//                Log.d("POSTALCODE****", String.valueOf(postalcode));
//                Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
//                Log.d("BUNDLE TEXT****", bundle.getString("test"));
//                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
//                Log.d("FULL ADDRESS****", fullAddress.toString());
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
