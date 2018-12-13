package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.LiveLocationListener;
import com.a6.projectgroep.bestofbreda.Services.database.RouteRepository;
import com.a6.projectgroep.bestofbreda.View.Activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RouteListViewModel extends AndroidViewModel {
    private RouteRepository repository;
    private LiveData<List<RouteModel>> liveData;

    public RouteListViewModel(@NonNull Application application) {
        super(application);
        repository = new RouteRepository(application);
        liveData = repository.getAllRouteModels();
    }

    public void selectRoute(int routeNumber) {
        GoogleMapsAPIManager.getInstance(getApplication(), new LiveLocationListener() {
            @Override
            public void onLocationChanged(LatLng latLng) {

            }
        }).setCurrentRoute(repository.getAllRouteModels().getValue().get(routeNumber));
    }

    public void insertRouteModel(RouteModel model) {
        repository.insertRouteModel(model);
    }

    public void updateRouteModel(RouteModel model) {
        repository.updateRouteModel(model);
    }

    public void deleteRouteModel(RouteModel model) {
        repository.deleteRouteModel(model);
    }

    public LiveData<List<RouteModel>> getAllRouteModels() {
        return liveData;
    }
}
