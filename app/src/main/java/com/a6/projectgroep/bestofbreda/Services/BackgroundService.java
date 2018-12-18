package com.a6.projectgroep.bestofbreda.Services;

import android.app.IntentService;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

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

        GoogleMapsAPIManager.getInstance(getApplication()).getAvailableWayPoints().observe((LifecycleOwner) this.getApplicationContext(), waypointModels -> {
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
                        if (currentPosition.distanceTo(waypointLocation) < 200) {
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
