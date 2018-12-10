package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.location.Location;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    public GoogleMapsAPIManager mapsApiManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mapsApiManager = GoogleMapsAPIManager.getInstance(application);
    }

    public LatLng getCurrentPosition()
    {
        return mapsApiManager.getCurrentPosition();
    }

    public ArrayList<WayPointModel> getAllWaypoints(){
        return mapsApiManager.getRouteWaypoints(getApplication());
    }
}
