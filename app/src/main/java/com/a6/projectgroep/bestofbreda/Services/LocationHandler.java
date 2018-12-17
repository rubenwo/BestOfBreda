package com.a6.projectgroep.bestofbreda.Services;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class LocationHandler {
    private static LocationHandler instance;
    private Application application;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currentLocation;
    private String provider;
    private boolean providerOn;

    public static LocationHandler getInstance(Application application)
    {
        if(instance == null) {
            instance = new LocationHandler(application);
        }
        return instance;
    }

    private LocationHandler(Application application) {
        this.application = application;
        provider = LocationManager.NETWORK_PROVIDER;
        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        providerOn = false;
    }

    public LatLng getCurrentLocation() {
        try {
            return new LatLng(locationManager.getLastKnownLocation(provider).getLatitude(), locationManager.getLastKnownLocation(provider).getLongitude());
        }
        catch(SecurityException e) {
            return null;
        }
    }

    public void setLiveLocationListener(LiveLocationListener listener){
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                listener.onLocationChanged(new LatLng(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                providerOn = true;
            }

            @Override
            public void onProviderDisabled(String provider) {
                providerOn = false;
                Toast.makeText(application.getApplicationContext(), "Je GPS functie staat niet aan", Toast.LENGTH_LONG).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(application.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.getLastKnownLocation(provider) != null) {
                locationManager.requestLocationUpdates(provider, 5, 2, locationListener);
                currentLocation = new LatLng(locationManager.getLastKnownLocation(provider).getLatitude(), locationManager.getLastKnownLocation(provider).getLongitude());
            }
        }
    }
}