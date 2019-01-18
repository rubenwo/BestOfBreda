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
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GoogleMapsAPIManager
{
    private static GoogleMapsAPIManager instance;
    private Application application;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private LatLng userLocation;

    private MutableLiveData<List<WaypointModel>> availableWayPoints;

    private MutableLiveData<Location> userCurrentLocation;
    private MutableLiveData<RouteModel> userSelectedRoute;
    private MutableLiveData<WaypointModel> nearbyWaypoint;
    private MutableLiveData<List<LatLng>> routePoints;

    Timer timer;

    public static GoogleMapsAPIManager getInstance(Application application)
    {
        if (instance == null)
            instance = new GoogleMapsAPIManager(application);
        return instance;
    }

    private GoogleMapsAPIManager(Application application)
    {
        this.application = application;
        userLocation = null;
        timer = new Timer();

        userCurrentLocation = new MutableLiveData<>();
        userSelectedRoute = new MutableLiveData<>();
        nearbyWaypoint = new MutableLiveData<>();
        routePoints = new MutableLiveData<>();

        userSelectedRoute.setValue(null);
        nearbyWaypoint.setValue(null);

        locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                System.out.println("LOCATIE GEVONDEN!!!!!!!!!!!!!!!!!!!!!");
                userCurrentLocation.setValue(location);
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                calculateNearbyWaypoint();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

            }
        };

        startLocationChanges();
    }

    public LiveData<Location> getCurrentLocation()
    {
        return userCurrentLocation;
    }

    public LiveData<RouteModel> getSelectedRoute()
    {
        return userSelectedRoute;
    }

    public LiveData<List<WaypointModel>> getAvailableWayPoints()
    {
        if (availableWayPoints == null) {
            availableWayPoints = (MutableLiveData<List<WaypointModel>>) Transformations.switchMap(userSelectedRoute, input ->
            {
                if (input != null) {
                    return NavigationDatabase.getInstance(application).waypointDAO().getAllWaypointModelsFromNames(input.getRoute());
                } else {
                    return NavigationDatabase.getInstance(application).waypointDAO().getAllWayPoints();
                }
            });
        }
        return availableWayPoints;
    }

    public LiveData<WaypointModel> getNearbyWayPoint()
    {
        return nearbyWaypoint;
    }

    public LiveData<List<LatLng>> routePoints()
    {
        return routePoints;
    }

    public void setCurrentRoute(RouteModel route)
    {
        userSelectedRoute.setValue(route);
        //calculateRoute();
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                calculateRoute();
            }
        }, 0, 120000);
    }

    private void calculateRoute()
    {
        Location currentLoc = null;
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            while (currentLoc == null) {
                currentLoc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        }

        userLocation = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
        if (userLocation != null) {
            new Thread(() ->
            {
                VolleyConnection connection = VolleyConnection.getInstance(application);
                List<LatLng> routePositions = new ArrayList<>();
                List<WaypointModel> tempPoints = new ArrayList<>();
                List<WaypointModel> points = NavigationDatabase.getInstance(application).waypointDAO().getAllWaypointModelsFromNamesNotLive(userSelectedRoute.getValue().getRoute());
                Log.i("WayPoints", points.toString());

                for (String s : userSelectedRoute.getValue().getRoute()) {
                    for (WaypointModel point : points) {
                        if (point.getName().equals(s)) {
                            tempPoints.add(point);
                            break;
                        }
                    }
                }
                Log.i("WayPoints", tempPoints.toString());

                routePositions.add(userLocation);
                for (WaypointModel model : tempPoints) {
                    if (!model.isAlreadySeen())
                        routePositions.add(model.getLocation());
                }

                connection.getRoute(routePositions,
                        response ->
                        {
                            try {
                                System.out.println(response);
                                routePositions.clear();
                                JSONArray legs = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
                                for (int i = 0; i < legs.length(); i++) {
                                    JSONArray jsonArray = legs.getJSONObject(i).getJSONArray("steps");
                                    for (int idx = 0; idx < jsonArray.length(); idx++) {
                                        routePositions.addAll(PolyUtil.decode(jsonArray.getJSONObject(idx).getJSONObject("polyline").getString("points")));
                                    }
                                }


                                routePoints.postValue(routePositions);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error ->
                        {
                            Log.i("route", "NOK");
                        });
            }).start();
        }
    }

    private void calculateNearbyWaypoint()
    {
        Location currentLocation = userCurrentLocation.getValue();
        if (availableWayPoints != null) {
            List<WaypointModel> waypointOnRoute = availableWayPoints.getValue();
            if (waypointOnRoute != null) {
                WaypointModel nearby = null;
                for (WaypointModel model : waypointOnRoute) {
                    Location modelLocation = new Location(model.getName());
                    modelLocation.setLatitude(model.getLocation().latitude);
                    modelLocation.setLongitude(model.getLocation().longitude);

                    if (currentLocation.distanceTo(modelLocation) <= 30) {
                        nearby = model;
                        //model.setAlreadySeen(true);
                        availableWayPoints.setValue(waypointOnRoute);
                        break;
                    }
                }

                if (nearby != null) {
                    System.out.println("IN DE BUUUUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRTTTT");
                } else {
                    System.out.println("NIET IN DE BUUUUUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRRRRTTTTTTT");
                }
                nearbyWaypoint.setValue(nearby);
            }
        }
    }

    public void startLocationChanges()
    {
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 2000, 20, locationListener);
        }
    }

    public void stopLocationChanges()
    {
        locationManager.removeUpdates(locationListener);
    }
}
