package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Services.DatabaseHandler;

import java.util.ArrayList;

public class RouteListViewModel extends AndroidViewModel {
    private ArrayList<RouteModel> routes;
    private RouteModel routeModel;

    public RouteListViewModel(@NonNull Application application) {
        super(application);
        routes = DatabaseHandler.getInstance(application.getApplicationContext()).routeDAO().getAllRoutes();
        routeModel = new RouteModel(routes.get(0).getRoute(), routes.get(0).getName());
    }

    public void selectRoute(int routeNumber){
        //TODO no way dat dit goed werkt zo
        if(routes.size() > routeNumber)
            routeModel.setRoute(routes.get(routeNumber).getRoute());
        else
            Log.i("[ERROR]", "Route not found!");
    }
}
