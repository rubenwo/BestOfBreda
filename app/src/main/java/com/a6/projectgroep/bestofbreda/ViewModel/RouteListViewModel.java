package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.DatabaseHandler;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.database.RouteRepository;

import java.util.ArrayList;
import java.util.List;

public class RouteListViewModel extends AndroidViewModel {
    private RouteRepository repository;
    private LiveData<List<RouteModel>> liveData;

    public RouteListViewModel(@NonNull Application application) {
        super(application);
        repository = new RouteRepository(application);
        liveData = repository.getAllTestData();
    }

    public void selectRoute(int routeNumber){
        //TODO no way dat dit goed werkt zo
            GoogleMapsAPIManager.getInstance(getApplication()).setCurrentRoute(repository.getAllTestData().getValue().get(routeNumber));
    }

    public void insertRouteModel(RouteModel model) {
        repository.insertMultiMedia(model);
    }

    public void updateRouteModel(RouteModel model) {
        repository.updateTestData(model);
    }

    public void deleteRouteModel(RouteModel model) {
        repository.deleteTestData(model);
    }

    public LiveData<List<RouteModel>> getAllRouteModels() {
        return liveData;
    }
}
