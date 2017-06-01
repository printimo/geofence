package com.nick.geofence;

import android.app.Application;
import android.content.Context;

/**
 * Created by root on 1/6/17.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
