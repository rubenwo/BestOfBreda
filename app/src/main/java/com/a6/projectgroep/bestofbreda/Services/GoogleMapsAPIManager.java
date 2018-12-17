package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;
import android.location.LocationManager;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
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
        locationHandler = LocationHandler.getInstance(application);
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
        List<String> strings = Arrays.asList("Test", "Test2");
        String string = "testString";
        MultimediaModel model = new MultimediaModel(strings, string);
        testWayPoints.add(new WaypointModel("avans breda", "avans hogeschool", GeoCoderService.getInstance(application).getLocationFromName("Avans breda"), false, false, model));
        testWayPoints.add(new WaypointModel("casino", "Holland Casino Breda", GeoCoderService.getInstance(application).getLocationFromName("holland casino breda"), false, false, model));
        testWayPoints.add(new WaypointModel("station breda", "Centraal Station Breda", GeoCoderService.getInstance(application).getLocationFromName("station breda"), false, false, model));
        return testWayPoints;
    }

    public WaypointModel getCurrentWaypoint() {
        return this.currentWaypoint;
    }

    public void SetCurrentWaypoint(WaypointModel currentWaypoint) {
        this.currentWaypoint = currentWaypoint;
    }
}
