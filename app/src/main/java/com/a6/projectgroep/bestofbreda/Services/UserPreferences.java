package com.a6.projectgroep.bestofbreda.Services;

import android.content.SharedPreferences;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

import java.util.ArrayList;

public class UserPreferences {
    private static UserPreferences instance;
    private SharedPreferences preferences;

    private UserPreferences() {

    }

    public static UserPreferences getInstance() {
        if (instance == null)
            instance = new UserPreferences();
        return instance;
    }

    public void setSeenWaypoints(WayPointModel waypoint) {

    }

    public void setDoneRoute(RouteModel route) {

    }

    public ArrayList<WayPointModel> getSeenWayPoints() {
        return null;
    }

    public ArrayList<RouteModel> getDoneRoutes() {
        return null;
    }

    public RouteModel getCurrentRoute() {
        return null;
    }

    public void setCurrentRoute(RouteModel route) {

    }


}
