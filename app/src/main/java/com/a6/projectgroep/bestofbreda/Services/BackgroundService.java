package com.a6.projectgroep.bestofbreda.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BackgroundService extends IntentService implements LiveLocationListener {

    private GoogleMapsAPIManager googleMapsAPIManager;
    private List<WayPointModel> wayPoints;
    private PushNotification pushNotification;

    public BackgroundService(String name) {
        super(name);
    }

    public BackgroundService() {
        super("back");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        googleMapsAPIManager = GoogleMapsAPIManager.getInstance(getApplication());
        wayPoints = googleMapsAPIManager.getRouteWaypoints(getApplication());
        pushNotification = PushNotification.getInstance(getApplicationContext());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    LatLng currentPosition = googleMapsAPIManager.getCurrentPosition();
                    Location currentLocation = new Location("currentLocation");
                    currentLocation.setLatitude(currentPosition.latitude);
                    currentLocation.setLongitude(currentPosition.longitude);
                    for(WayPointModel wayPointModel : wayPoints)
                    {
                        Location wayPointLocation = new Location("WayPointLocation");
                        wayPointLocation.setLongitude(wayPointModel.getLocation().longitude);
                        wayPointLocation.setLatitude(wayPointModel.getLocation().latitude);
                        if(currentLocation.distanceTo(wayPointLocation) < 200)
                        {
                            if(!wayPointModel.isAlreadySeen()) {
                                pushNotification.SendSightNotification(wayPointModel.getName(), wayPointModel.getDescription(), getApplicationContext());
                                wayPointModel.setAlreadySeen(true);
                            }
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onLocationChanged(LatLng latLng) {

    }
}
