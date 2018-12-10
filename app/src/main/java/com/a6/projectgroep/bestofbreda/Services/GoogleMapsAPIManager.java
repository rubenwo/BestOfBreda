package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.location.LocationManager;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class GoogleMapsAPIManager {
    private static GoogleMapsAPIManager instance;
    private GeofencingClient geoManager;
    private LocationManager gpsManager;
    private LatLng currentPosition;
    private RouteModel currentRoute;
    private WayPointModel currentWaypoint;
    private ArrayList<WayPointModel> waypoints;

    public GoogleMapsAPIManager(Context context) {
    }

    public static GoogleMapsAPIManager getInstance(Context context) {
        if (instance == null)
            instance = new GoogleMapsAPIManager(context);
        return instance;
    }

    public void calculateRoute() {

    }

    public void setCurrentRoute(RouteModel route) {
        this.currentRoute = route;
    }

    public void getRouteWaypoints() {
    }

    public WayPointModel getCurrentWaypoint() {
        return this.currentWaypoint;
    }

    public LatLng getCurrentPosition() {
        return this.currentPosition;
    }


}
