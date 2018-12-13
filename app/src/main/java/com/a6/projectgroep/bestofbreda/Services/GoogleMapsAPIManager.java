package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;
import android.location.LocationManager;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
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
    private WaypointModel currentWaypoint;
    private ArrayList<WaypointModel> waypoints;
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

    public List<WaypointModel> getRouteWaypoints(Application application) {
        List<WaypointModel> testWayPoints = new ArrayList<>();
        testWayPoints.add(new WaypointModel(1, "avans breda", "avans hogeschool", GeoCoderService.getInstance(application).getLocationFromName("Avans breda"), false, false, 1));
        testWayPoints.add(new WaypointModel(2, "casino", "Holland Casino Breda", GeoCoderService.getInstance(application).getLocationFromName("holland casino breda"), false, false, 2));
        testWayPoints.add(new WaypointModel(3, "station breda", "Centraal Station Breda", GeoCoderService.getInstance(application).getLocationFromName("station breda"), false, false, 3));
        return testWayPoints;
    }

    public WaypointModel getCurrentWaypoint() {
        return this.currentWaypoint;
    }

    public void SetCurrentWaypoint(WaypointModel currentWaypoint) {
        this.currentWaypoint = currentWaypoint;
    }

    public LatLng getCurrentPosition() {
        currentPosition = locationHandler.getCurrentLocation();
        return this.currentPosition;
    }
}
