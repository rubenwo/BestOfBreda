package com.a6.projectgroep.bestofbreda.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;

import java.util.List;

public class BackgroundService extends IntentService {
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
        googleMapsAPIManager = GoogleMapsAPIManager.getInstance(getApplication());
        pushNotification = PushNotification.getInstance(getApplicationContext());

        GoogleMapsAPIManager.getInstance(getApplication()).getAvailableWayPoints().observeForever( waypointModels -> {
            wayPoints = waypointModels;
        });

        Thread thread = new Thread(() -> {
            while (true) {
                Location currentPosition = googleMapsAPIManager.getCurrentLocation().getValue();
                if (currentPosition != null) {
                    //TODO: mogelijk null check weghalen en anders oplossen, voor nu werkt dit.
                    for (WaypointModel waypointModel : wayPoints) {
                        Location waypointLocation = new Location("WayPointLocation");
                        waypointLocation.setLongitude(waypointModel.getLocation().longitude);
                        waypointLocation.setLatitude(waypointModel.getLocation().latitude);
//                        Log.i("BackgroundService", "check for every waypoint");

                        if (currentPosition.distanceTo(waypointLocation) < 30) {
                            if (!waypointModel.isAlreadySeen()) {
                                pushNotification.SendSightNotification(waypointModel.getName(), waypointModel.getDescriptionEN(), getApplicationContext());
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
        });
        thread.start();
    }


}
