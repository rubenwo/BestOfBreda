package com.a6.projectgroep.bestofbreda.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BackgroundService extends IntentService implements LiveLocationListener {

    private GoogleMapsAPIManager googleMapsAPIManager;
    private List<WaypointModel> wayPoints;
    private PushNotification pushNotification;

    public BackgroundService(String name) {
        super(name);
    }

    public BackgroundService() {
        super("back");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        googleMapsAPIManager = GoogleMapsAPIManager.getInstance(getApplication(), this);
        pushNotification = PushNotification.getInstance(getApplicationContext());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    LatLng currentPosition = LocationHandler.getInstance(getApplication()).getCurrentLocation();
                    if (currentPosition != null) {
                        //TODO: mogelijk null check weghalen en anders oplossen, voor nu werkt dit.
                        Location currentLocation = new Location("currentLocation");
                        currentLocation.setLatitude(currentPosition.latitude);
                        currentLocation.setLongitude(currentPosition.longitude);
                        for (WaypointModel waypointModel : wayPoints) {
                            Location waypointLocation = new Location("WayPointLocation");
                            waypointLocation.setLongitude(waypointModel.getLocation().longitude);
                            waypointLocation.setLatitude(waypointModel.getLocation().latitude);
                            if (currentLocation.distanceTo(waypointLocation) < 200) {
                                if (!waypointModel.isAlreadySeen()) {
                                    pushNotification.SendSightNotification(waypointModel.getName(), waypointModel.getDescription(), getApplicationContext());
                                    waypointModel.setAlreadySeen(true);
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
            }
        });
        thread.start();
    }

    @Override
    public void onLocationChanged(LatLng latLng) {

    }
}
