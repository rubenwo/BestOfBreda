package com.a6.projectgroep.bestofbreda.Services;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.R;
import com.google.android.gms.maps.model.LatLng;

public class LocationHandler {
    private static LocationHandler instance;

    private Application application;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng currentLocation;
    private String provider;
    private boolean providerOn;
    private boolean lastKnownLocationAvailable = false;

    public static LocationHandler getInstance(Application application) {
        if (instance == null) {
            instance = new LocationHandler(application);
        }
        return instance;
    }

    private LocationHandler(Application application) {
        this.application = application;
        provider = LocationManager.NETWORK_PROVIDER;
        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setLiveLocationListener(LiveLocationListener listener) {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (!lastKnownLocationAvailable) {
                    lastKnownLocationAvailable = true;
                }
            }

            @Override
            public void onStatusChanged(String statusProvider, int status, Bundle extras) {
                if (statusProvider.equals(provider) && status == LocationProvider.OUT_OF_SERVICE) {
                    Toast toast = new Toast(application);
                    toast.setText("FUK-KA U");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                providerOn = false;
                Toast.makeText(application.getApplicationContext(), R.string.GPS_off, Toast.LENGTH_LONG).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(application.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.getLastKnownLocation(provider) != null) {
                locationManager.requestLocationUpdates(provider, 5, 0, locationListener);
                currentLocation = new LatLng(locationManager.getLastKnownLocation(provider).getLatitude(), locationManager.getLastKnownLocation(provider).getLongitude());
            }
        }
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }


}