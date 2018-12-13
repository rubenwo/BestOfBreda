package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsAPIManager {
    private static GoogleMapsAPIManager instance;
    private GeofencingClient geoManager;
    private LocationManager gpsManager;
    private LatLng currentPosition;
    private RouteModel currentRoute;
    private WayPointModel currentWaypoint;
    private ArrayList<WayPointModel> waypoints;
    private LocationHandler locationHandler;

    private GoogleMapsAPIManager(Application application, LiveLocationListener listener) {
        locationHandler = LocationHandler.getInstance(application, listener);
    }

    public static GoogleMapsAPIManager getInstance(Application application, LiveLocationListener listener) {
        if (instance == null)
            instance = new GoogleMapsAPIManager(application, listener);
        return instance;
    }

    public void calculateRoute() {

    }

    public void setCurrentRoute(RouteModel route) {
        this.currentRoute = route;
    }

    public List<WayPointModel> getRouteWaypoints(Application application) {
        List<WayPointModel> testWayPoints = new ArrayList<>();
        testWayPoints.add(new WayPointModel("avans breda", GeoCoderService.getInstance(application).getLocationFromName("Avans breda"), "", 0, false, false, null));
        testWayPoints.add(new WayPointModel("casino", GeoCoderService.getInstance(application).getLocationFromName("holland casino breda"), "", 0, false, false, null));
        testWayPoints.add(new WayPointModel("station breda", GeoCoderService.getInstance(application).getLocationFromName("station breda"), "", 0, false, false, null));
//        return currentRoute.getWaypointList();
        return testWayPoints;
    }

    public WayPointModel getCurrentWaypoint() {
        return this.currentWaypoint;
    }

    public void SetCurrentWaypoint(WayPointModel currentWaypoint){
        this.currentWaypoint = currentWaypoint;
    }

    public LatLng getCurrentPosition() {
        currentPosition = locationHandler.getCurrentLocation();
        return this.currentPosition;
    }
}
