package com.nick.geofence;

import java.util.List;

/**
 * Created by root on 1/6/17.
 */

public class GeofenceList {

    private List<GeofenceInfo> list;

    public GeofenceList(List<GeofenceInfo> list) {
        this.list = list;
    }

    public List<GeofenceInfo> getList() {
        return list;
    }

    public void setList(List<GeofenceInfo> list) {
        this.list = list;
    }
}
