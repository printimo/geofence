package com.nick.geofence;

/**
 * Created by root on 1/6/17.
 */

public class Geofence {

    private long longitude;
    private long latitude;
    private int radius; // meters
    private String wifiName;

    public Geofence(long longt, long lat, int rad, String wifi) {
        longitude = longt;
        latitude = lat;
        wifiName = wifi;
        radius = rad;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
