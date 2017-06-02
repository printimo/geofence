package com.nick.geofence;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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

    private List<GeofenceInfo> list = new ArrayList<>();
    private Activity activity;
    private Location currentLocation;
    private String currentWifi;

    public GeofenceAdapter(Activity act, List<GeofenceInfo> arr) {
        list = arr;
        activity = act;
    }

    @Override
    public GeofenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeofenceViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.list_item,null));
    }

    @Override
    public void onBindViewHolder(GeofenceViewHolder holder, final int position) {
        holder.latitude.setText("Latitude : " + String.valueOf(list.get(position).getLatitude()));
        holder.longitude.setText("Longitude : " + String.valueOf(list.get(position).getLongitude()));
        holder.radius.setText("Radius : " + String.valueOf(list.get(position).getRadius() + " m"));
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
        if (currentLocation!=null) {
            if (distFrom(currentLocation.getLatitude(),currentLocation.getLongitude(),
                    list.get(position).getLatitude(),list.get(position).getLongitude()) < list.get(position).getRadius()
                    || (currentWifi!=null && currentWifi.equals(list.get(position).getWifiName()))) {
                holder.inside.setText("inside");
                holder.inside.setTextColor(App.getContext().getResources().getColor(R.color.green));
            } else {
                holder.inside.setText("outside");
                holder.inside.setTextColor(App.getContext().getResources().getColor(R.color.red));
            }
        } else {
            holder.inside.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(GeofenceInfo newGeofence) {
        list.add(newGeofence);
        notifyDataSetChanged();
    }

    public List<GeofenceInfo> getList() {
        return list;
    }

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        notifyDataSetChanged();
    }

    public void setCurrentWifi(String currentWifi) {
        this.currentWifi = currentWifi;
        notifyDataSetChanged();
    }

    public static class GeofenceViewHolder extends RecyclerView.ViewHolder {

        public TextView longitude;
        public TextView latitude;
        public TextView radius;
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
            radius = (TextView) itemView.findViewById(R.id.list_item_radius);
        }
    }

}
