package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;

import java.util.ArrayList;

public interface RouteDAO {
    ArrayList<RouteModel> getAllRoutes();

    LiveData<RouteModel> getLiveRoute(String routeName);

    void insertRoute(RouteModel route);
}
