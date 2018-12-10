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

public class LocationHandler {
    private Application application;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private String provider;
    private boolean providerOn;

    public LocationHandler(Application application) {
        this.application = application;
        provider = LocationManager.NETWORK_PROVIDER;
        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        providerOn = false;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                System.out.println(location.getLatitude());
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
                //Toast.makeText(MapsActivity.this, "Je GPS functie staat niet aan", Toast.LENGTH_LONG).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(application.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && providerOn) {
            locationManager.requestLocationUpdates(provider, 5, 2, locationListener);
            currentLocation = locationManager.getLastKnownLocation(provider);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}