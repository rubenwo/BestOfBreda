package com.a6.projectgroep.bestofbreda.Services;

import android.Manifest;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.database.NavigationDatabase;

import java.util.List;

public class GoogleMapsAPIManager {
    private static GoogleMapsAPIManager instance;
    private Application application;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private LiveData<List<WaypointModel>> availableWayPoints;

    private MutableLiveData<Location> userCurrentLocation;
    private MutableLiveData<RouteModel> userSelectedRoute;
    private MutableLiveData<WaypointModel> nearbyWaypoint;

    public static GoogleMapsAPIManager getInstance(Application application) {
        if (instance == null)
            instance = new GoogleMapsAPIManager(application);
        return instance;
    }

    private GoogleMapsAPIManager(Application application) {
        this.application = application;

        userCurrentLocation = new MutableLiveData<>();
        userSelectedRoute = new MutableLiveData<>();
        userSelectedRoute.setValue(null);
        nearbyWaypoint = new MutableLiveData<>();


        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userCurrentLocation.setValue(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        startLocationChanges();
    }

    public LiveData<Location> getCurrentLocation() {
        return userCurrentLocation;
    }

    public LiveData<RouteModel> getSelectedRoute() {
        return userSelectedRoute;
    }

    public LiveData<List<WaypointModel>> getAvailableWayPoints() {
        if(availableWayPoints == null) {
            availableWayPoints = Transformations.switchMap(userSelectedRoute, input -> {
                if (input != null) {
                    return NavigationDatabase.getInstance(application).waypointDAO().getAllWaypointModelsFromNames(input.getRoute());
                } else {
                    return NavigationDatabase.getInstance(application).waypointDAO().getAllWayPoints();
                }
            });
        }
        return availableWayPoints;
    }

    public LiveData<WaypointModel> getNearbyWayPoint() {
        return nearbyWaypoint;
    }

    public void setCurrentRoute(RouteModel route) {
        userSelectedRoute.setValue(route);
    }

    public void calculateRoute() {
        //
    }

    private void calculateNearbyWaypoint() {

    }

    public void startLocationChanges() {
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, locationListener);
        }
    }

    public void stopLocationChanges() {
        locationManager.removeUpdates(locationListener);
    }

//    public WaypointModel getCurrentWaypoint() {
//        return this.currentWaypoint;
//    }
//
//    public void SetCurrentWaypoint(WaypointModel currentWaypoint) {
//        this.currentWaypoint = currentWaypoint;
//    }
}
