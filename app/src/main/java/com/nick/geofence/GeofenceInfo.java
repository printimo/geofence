package com.nick.geofence;

/**
 * Created by root on 1/6/17.
 */

public class GeofenceInfo {

    private double longitude;
    private double latitude;
    private int radius; // meters
    private String wifiName;

    public GeofenceInfo(double longt, double lat, int rad, String wifi) {
        longitude = longt;
        latitude = lat;
        wifiName = wifi;
        radius = rad;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
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
