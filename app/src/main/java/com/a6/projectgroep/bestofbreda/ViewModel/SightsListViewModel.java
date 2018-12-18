package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.database.RouteRepository;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;

import java.util.List;

public class SightsListViewModel extends AndroidViewModel {
    private RouteRepository repository;
    private LiveData<List<WaypointModel>> liveData;

    public SightsListViewModel(@NonNull Application application) {
        super(application);
        repository = new RouteRepository(application);
        liveData = GoogleMapsAPIManager.getInstance(application).getAvailableWayPoints();
    }

    public LiveData<List<WaypointModel>> getAllWaypoinModels() {
        return liveData;
    }
}