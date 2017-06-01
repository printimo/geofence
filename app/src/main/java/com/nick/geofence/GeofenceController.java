package com.nick.geofence;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.UUID;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by root on 1/6/17.
 */

class GeofenceController {
    private static List<GeofenceInfo> list;

    public static void setList(List<GeofenceInfo> list) {
        GeofenceController.list = list;
        for (GeofenceInfo geofence:list) {
            add(geofence);
        }
    }

    public static void add(GeofenceInfo newGeofence) {
//        Geofence geofence = new Geofence.Builder()
//                .setRequestId(UUID.randomUUID().toString()) // GeofenceInfo ID
//                .setCircularRegion( newGeofence.getLatitude(), newGeofence.getLongitude(), newGeofence.getRadius()) // defining fence region
//                // Transition types that it should look for
//                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_DWELL )
//                .build();
//        GeofencingRequest request = new GeofencingRequest.Builder()
//                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_DWELL )
//                .addGeofence( geofence ) // add a Geofence
//                .build();
    }

//    private void addGeofence(GeofencingRequest request) {
//        LocationServices.GeofencingApi.addGeofences(
//                googleApiClient,
//                request,
//                createGeofencePendingIntent()
//        ).setResultCallback(this);
//    }
//
//    private PendingIntent createGeofencePendingIntent() {
//        Log.d(TAG, "createGeofencePendingIntent");
//        if ( geoFencePendingIntent != null )
//            return geoFencePendingIntent;
//
//        Intent intent = new Intent( this, GeofenceTrasitionService.class);
//        return PendingIntent.getService(
//                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
//    }
}
