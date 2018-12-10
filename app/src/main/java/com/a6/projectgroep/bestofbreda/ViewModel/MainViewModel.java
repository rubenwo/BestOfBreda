package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.location.Location;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.VolleyConnection;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    public GoogleMapsAPIManager mapsApiManager;
    public VolleyConnection volleyConnection;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mapsApiManager = GoogleMapsAPIManager.getInstance(application);
        volleyConnection = VolleyConnection.getInstance(application.getApplicationContext());
    }

    public LatLng getCurrentPosition()
    {
        return mapsApiManager.getCurrentPosition();
    }

    public ArrayList<WayPointModel> getAllWaypoints(){
        return mapsApiManager.getRouteWaypoints(getApplication());
    }

    public ArrayList<LatLng> getRoute(LatLng origin, LatLng dest)
    {
        return volleyConnection.getRoute(origin, dest);
    }

}
