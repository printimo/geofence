package com.nick.geofence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by root on 1/6/17.
 */

public class DataPrefences {

    private static final String NAME = "data";
    private static final String LIST = "list";
    private static SharedPreferences preferences;

    public static SharedPreferences getInstance() {
        if (preferences == null) {
            preferences = App.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static GeofenceList getGeofenceList() {
        return new Gson().fromJson(getInstance().getString(LIST,""), GeofenceList.class);
    }

    public static void saveGeofenceList(List<Geofence> list) {
        getInstance().edit().putString(LIST, new Gson().toJson(new GeofenceList(list))).apply();
    }

}
