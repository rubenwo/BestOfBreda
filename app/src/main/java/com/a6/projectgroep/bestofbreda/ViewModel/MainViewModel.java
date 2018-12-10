package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public GoogleMapsAPIManager mapsApiManager;
    private WaypointRepository repository;
    private LiveData<List<WayPointModel>> liveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mapsApiManager = GoogleMapsAPIManager.getInstance(application);
        repository = new WaypointRepository(application);
        liveData = repository.getAllTestData();
    }

    public LatLng getCurrentPosition()
    {
        return mapsApiManager.getCurrentPosition();
    }

    public void insertWayPointModel(WayPointModel model) {
        repository.insertMultiMedia(model);
    }

    public void updateWayPointModel(WayPointModel model) {
        repository.updateTestData(model);
    }

    public void deleteWayPointModel(WayPointModel model) {
        repository.deleteTestData(model);
    }

    public LiveData<List<WayPointModel>> getAllWaypointModels() {
        return liveData;
    }
}
