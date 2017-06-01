package com.nick.geofence;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_GEOFECE = 10;
    private GeofenceAdapter adapter;
    private LocationListener listener;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View add = findViewById(R.id.add_geofence);
        RecyclerView listView = (RecyclerView) findViewById(R.id.geofence_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);

        GeofenceList geolist = DataPrefences.getGeofenceList();
        List<Geofence> list = new ArrayList<>();

        if (geolist == null || geolist.getList() == null) {

            list.add(new Geofence(20, 30, 100, ""));
            list.add(new Geofence(30, 35, 100, "DataHub"));
        } else {
            list = geolist.getList();
        }

        listView.setAdapter(adapter = new GeofenceAdapter(this, list));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddGeofence.class), ADD_GEOFECE);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Geofence newGeofence = new Geofence(data.getDoubleExtra("long",0),
                data.getDoubleExtra("lat",0),data.getIntExtra("radius",1),data.getStringExtra("wifi"));
        adapter.add(newGeofence);
        DataPrefences.saveGeofenceList(adapter.getList());
    }
}
