package com.nick.geofence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/6/17.
 */

public class GeofenceList {

    private List<Geofence> list;

    public GeofenceList(List<Geofence> list) {
        this.list = list;
    }

    public List<Geofence> getList() {
        return list;
    }

    public void setList(List<Geofence> list) {
        this.list = list;
    }
}
