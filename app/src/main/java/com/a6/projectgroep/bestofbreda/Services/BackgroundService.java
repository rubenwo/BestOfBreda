package com.a6.projectgroep.bestofbreda.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BackgroundService extends IntentService implements LiveLocationListener {

    private LocationHandler locationHandler;
    private ArrayList<LatLng> positions;

    public BackgroundService(String name) {
        super(name);
    }

    public BackgroundService() {
        super("back");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        locationHandler = LocationHandler.getInstance(getApplication(), this);
        positions = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(locationHandler != null) {
                        positions.add(locationHandler.getCurrentLocation());
                        Log.d("background", String.valueOf(positions.size()));
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
