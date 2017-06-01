package com.nick.geofence;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/6/17.
 */

public class GeofenceAdapter extends RecyclerView.Adapter<GeofenceAdapter.GeofenceViewHolder>{

    private List<Geofence> list = new ArrayList<>();

    public GeofenceAdapter(List<Geofence> arr) {
        list = arr;
    }

    @Override
    public GeofenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeofenceViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.list_item,null));
    }

    @Override
    public void onBindViewHolder(GeofenceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GeofenceViewHolder extends RecyclerView.ViewHolder {

        public GeofenceViewHolder(View itemView) {
            super(itemView);
        }
    }

}
