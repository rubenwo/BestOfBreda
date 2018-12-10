package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;

import java.util.List;

public class DetailedRouteViewModel extends AndroidViewModel {
    private WaypointRepository repository;
    private LiveData<List<WayPointModel>> liveData;

    public DetailedRouteViewModel(@NonNull Application application) {
        super(application);
        repository = new WaypointRepository(application);
        liveData = repository.getAllTestData();
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
