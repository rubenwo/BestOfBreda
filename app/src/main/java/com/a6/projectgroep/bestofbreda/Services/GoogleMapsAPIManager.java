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
import android.util.Log;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.database.NavigationDatabase;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsAPIManager {
    private static GoogleMapsAPIManager instance;
    private Application application;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private LatLng userLocation;

    private LiveData<List<WaypointModel>> availableWayPoints;

    private MutableLiveData<Location> userCurrentLocation;
    private MutableLiveData<RouteModel> userSelectedRoute;
    private MutableLiveData<WaypointModel> nearbyWaypoint;
    private MutableLiveData<List<LatLng>> routePoints;

    public static GoogleMapsAPIManager getInstance(Application application) {
        if (instance == null)
            instance = new GoogleMapsAPIManager(application);
        return instance;
    }

    private GoogleMapsAPIManager(Application application) {
        this.application = application;
        userLocation = null;

        userCurrentLocation = new MutableLiveData<>();
        userSelectedRoute = new MutableLiveData<>();
        nearbyWaypoint = new MutableLiveData<>();
        routePoints = new MutableLiveData<>();

        userSelectedRoute.setValue(null);
        nearbyWaypoint.setValue(null);

        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("LOCATIE GEVONDEN!!!!!!!!!!!!!!!!!!!!!");
                userCurrentLocation.setValue(location);
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());
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
        if (availableWayPoints == null) {
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

    public LiveData<List<LatLng>> routePoints() {
        return routePoints;
    }

    public void setCurrentRoute(RouteModel route) {
        userSelectedRoute.setValue(route);
        calculateRoute();
    }

    private void calculateRoute() {
        if(userLocation != null) {
            new Thread(() -> {
                VolleyConnection connection = VolleyConnection.getInstance(application);
                List<LatLng> routePositions = new ArrayList<>();
                List<WaypointModel> points = NavigationDatabase.getInstance(application).waypointDAO().getAllWaypointModelsFromNamesNotLive(userSelectedRoute.getValue().getRoute());

                routePositions.add(userLocation);
                for (WaypointModel model : points) {
                    routePositions.add(model.getLocation());
                }

                connection.getRoute(routePositions,
                        response -> {
                            try {
                                System.out.println(response);
                                routePositions.clear();
                                JSONArray jsonArray = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
                                for (int idx = 0; idx < jsonArray.length(); idx++) {
                                    LatLng latLng = new LatLng(jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lat"), jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lng"));
                                    routePositions.add(latLng);
                                }

                                routePoints.postValue(routePositions);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                            Log.i("route", "NOK");
                        });
            }).start();
        }
    }

    private void calculateNearbyWaypoint() {

    }

    public void startLocationChanges() {
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 20, locationListener);
        }
    }

    public void stopLocationChanges() {
        locationManager.removeUpdates(locationListener);
    }
}
