package com.nick.geofence;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/6/17.
 */

public class GeofenceAdapter extends RecyclerView.Adapter<GeofenceAdapter.GeofenceViewHolder>{

    private List<Geofence> list = new ArrayList<>();
    private Activity activity;

    public GeofenceAdapter(Activity act, List<Geofence> arr) {
        list = arr;
        activity = act;
    }

    @Override
    public GeofenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeofenceViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.list_item,null));
    }

    @Override
    public void onBindViewHolder(GeofenceViewHolder holder, final int position) {
        holder.latitude.setText(String.valueOf(list.get(position).getLatitude()));
        holder.longitude.setText(String.valueOf(list.get(position).getLongitude()));
        holder.wifi.setText(String.valueOf(list.get(position).getWifiName()));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUri = "http://maps.google.com/maps?q=loc:" + list.get(position).getLatitude() + "," + list.get(position).getLongitude() + " (" + "Label which you want" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GeofenceViewHolder extends RecyclerView.ViewHolder {

        public TextView longitude;
        public TextView latitude;
        public TextView wifi;
        public TextView inside;
        public View card;

        public GeofenceViewHolder(View itemView) {
            super(itemView);
            longitude = (TextView) itemView.findViewById(R.id.list_item_longitude);
            latitude = (TextView) itemView.findViewById(R.id.list_item_latitude);
            wifi = (TextView) itemView.findViewById(R.id.list_item_wifi);
            inside = (TextView) itemView.findViewById(R.id.list_item_inside);
            card = itemView.findViewById(R.id.list_item_card_view);
        }
    }

}
