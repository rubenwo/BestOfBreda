package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

public class UserPreferences {
    private volatile static UserPreferences instance;
    private SharedPreferences preferences;
    private ArrayList<String> waypointKeys;
    private ArrayList<String> routeKeys;
    private String currentRouteKey;

    private UserPreferences(Application application) {
        preferences = application.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        waypointKeys = new ArrayList<>();
        routeKeys = new ArrayList<>();
        currentRouteKey = "";
        createAllKeys();
    }

    public static UserPreferences getInstance(Application application) {
        if (instance == null)
            instance = new UserPreferences(application);
        return instance;
    }

    private void createAllKeys() {
        Set<String> keys = preferences.getAll().keySet();
        for (String key : keys) {
            if (key.contains("WAYPOINT"))
                waypointKeys.add(key);
            else if (key.contains("ROUTE"))
                routeKeys.add(key);
            else if (key.contains("CURRENT"))
                currentRouteKey = key;
        }
    }

    public void setSeenWaypoints(WaypointModel waypoint) {
        waypointKeys.add(waypoint.getName());
        SharedPreferences.Editor editor = this.preferences.edit();
        waypoint.setAlreadySeen(true);
        Gson gson = new Gson();
        String serialized = gson.toJson(waypoint);
        editor.putString("WAYPOINT:" + waypoint.getName(), serialized);
        editor.apply();
    }

    public void setDoneRoute(RouteModel route) {
        routeKeys.add(route.getName());
        SharedPreferences.Editor editor = this.preferences.edit();
        route.setDone(true);
        Gson gson = new Gson();
        String serialized = gson.toJson(route);
        editor.putString("ROUTE:" + route.getName(), serialized);
        editor.apply();
    }

    public ArrayList<WaypointModel> getSeenWayPoints() {
        ArrayList<WaypointModel> models = new ArrayList<>();
        Gson gson = new Gson();
        for (String key : waypointKeys) {
            String serialized = preferences.getString(key, "ERROR");
            if (!serialized.equals("ERROR")) {
                models.add(gson.fromJson(serialized, WaypointModel.class));

            }
        }
        return models;
    }

    public ArrayList<RouteModel> getDoneRoutes() {
        ArrayList<RouteModel> models = new ArrayList<>();
        Gson gson = new Gson();
        for (String key : routeKeys) {
            String serialized = preferences.getString(key, "ERROR");
            if (!serialized.equals("ERROR")) {
                models.add(gson.fromJson(serialized, RouteModel.class));
            }
        }
        return models;
    }

    public RouteModel getCurrentRoute() {
        RouteModel currentRoute = null;
        Gson gson = new Gson();
        String serialized = preferences.getString(currentRouteKey, "ERROR");
        if (!serialized.equals("ERROR"))
            currentRoute = gson.fromJson(serialized, RouteModel.class);
        return currentRoute;
    }

    public void setCurrentRoute(RouteModel route) {
        SharedPreferences.Editor editor = this.preferences.edit();
        Gson gson = new Gson();
        String serialized = gson.toJson(route);
        editor.putString("CURRENT:" + route.getName(), serialized);
        editor.apply();
    }


    public void setFirstRun(boolean firstRun) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean("firstRun", firstRun);
        editor.apply();
    }

    public boolean getFirstRun() {
        return this.preferences.getBoolean("firstRun", true);
    }

}
