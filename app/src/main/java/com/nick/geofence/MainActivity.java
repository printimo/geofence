package com.nick.geofence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View add = findViewById(R.id.add_geofence);
        RecyclerView listView = (RecyclerView) findViewById(R.id.geofence_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(layoutManager);

        List<Geofence> list = new ArrayList<>();
        list.add(new Geofence(20,30,100,""));
        list.add(new Geofence(30,35,100,"DataHub"));

        listView.setAdapter(new GeofenceAdapter(this,list));
    }
}
