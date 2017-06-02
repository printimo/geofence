package com.nick.geofence;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_GEOFECE = 10;
    private static final int PERMISSIONS = 22;
    private GeofenceAdapter adapter;
    private LocationListener listener;
    LocationManager locationManager;
    private TextView locationText;
    private String provider;
    private Location currentLocation;
    private View searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = (TextView) findViewById(R.id.current_location);
        View add = findViewById(R.id.add_geofence);
        searching = findViewById(R.id.searching);
        RecyclerView listView = (RecyclerView) findViewById(R.id.geofence_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);

        GeofenceList geolist = DataPrefences.getGeofenceList();
        List<GeofenceInfo> list = new ArrayList<>();

        if (geolist == null || geolist.getList() == null) {

            list.add(new GeofenceInfo(20, 30, 150, "Wifi"));
            list.add(new GeofenceInfo(34.052, -118.2436, 20000, "Los Angeles"));
            list.add(new GeofenceInfo(50.449, 30.524, 5000, "Maidan"));
            GeofenceController.setList(list);
        } else {
            list = geolist.getList();
        }

        listView.setAdapter(adapter = new GeofenceAdapter(this, list));
        adapter.setCurrentWifi(getCurrentSSID(this));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGeofence.class);
                if (currentLocation!=null) {
                    intent.putExtra("lat",currentLocation.getLatitude());
                    intent.putExtra("long",currentLocation.getLongitude());
                } else {
                    intent.putExtra("lat",50.41);
                    intent.putExtra("long",30.66);
                }
                startActivityForResult(intent, ADD_GEOFECE);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?");    //set message

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.getList().remove(position);
                            DataPrefences.saveGeofenceList(adapter.getList());
                            adapter.notifyItemRemoved(position);    //item removed from recylcerview
                            return;
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            adapter.notifyItemRangeChanged(position, adapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show();  //show alert dialog
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listView);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.e("Trigger", "getNetworkInfo");
          //  Toast.makeText(App.getContext(), "connected to " + getCurrentSSID(App.getContext()),Toast.LENGTH_LONG).show();
            adapter.setCurrentWifi(getCurrentSSID(App.getContext()));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS);
        } else {
            getLocation();
        }
        registerReceiver(receiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            GeofenceInfo newGeofence = new GeofenceInfo(data.getDoubleExtra("lat", 0),
                    data.getDoubleExtra("long", 0), data.getIntExtra("radius", 1), data.getStringExtra("wifi"));
            adapter.add(newGeofence);
            DataPrefences.saveGeofenceList(adapter.getList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

               getLocation();

            }
        }
    }

    public void getLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                showLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        locationManager.requestLocationUpdates(provider, 400, 1, listener);

    }

    private void showLocation() {
        Location locationNet = null;
        try {
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (location == null) {
            location = locationNet;
        }

        if (location!=null) {
            searching.setVisibility(View.GONE);
            locationText.setText("Current location : \n" + location.getLatitude() + "\n" + location.getLongitude());
            adapter.setCurrentLocation(location);
            final Location finalLocation = location;
            locationText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openMap(finalLocation.getLatitude(), finalLocation.getLongitude());
                }
            });
        }

    }

    private void openMap(double latitude, double longitude) {
        String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null && listener != null) {
            locationManager.removeUpdates(listener);
        }
        listener = null;
        unregisterReceiver(receiver);
    }

    public static String getCurrentSSID(Context context) {
        String ssid = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                ssid = connectionInfo.getSSID().replace("\"","");
            }
        }

        return ssid;
    }
}
