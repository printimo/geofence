package com.nick.geofence;

/**
 * Created by root on 1/6/17.
 */

public class Geofence {

    private long longtitude;
    private long latitute;
    private int radius; // meters
    private String wifiName;

    public Geofence(long longt, long lat, int rad, String wifi) {
        longtitude = longt;
        latitute = lat;
        wifiName = wifi;
        radius = rad;
    }

    public long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(long longtitude) {
        this.longtitude = longtitude;
    }

    public long getLatitute() {
        return latitute;
    }

    public void setLatitute(long latitute) {
        this.latitute = latitute;
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
