package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.Services.database.MultimediaRepository;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;

import java.util.List;

public class DetailedViewModel extends AndroidViewModel {
    private WaypointRepository waypointRepository;
    private LiveData<List<WayPointModel>> liveDataWaypoint;
    private MultimediaRepository multimediaRepository;
    private LiveData<List<MultimediaModel>> liveDataMedia;

    public DetailedViewModel(@NonNull Application application) {
        super(application);
        waypointRepository = new WaypointRepository(application);
        liveDataWaypoint = waypointRepository.getAllTestData();
        multimediaRepository = new MultimediaRepository(application);
        liveDataMedia = multimediaRepository.getAllTestData();
    }

    public void insertWayPointModel(WayPointModel model) {
        waypointRepository.insertMultiMedia(model);
    }

    public void updateWayPointModel(WayPointModel model) {
        waypointRepository.updateTestData(model);
    }

    public void deleteWayPointModel(WayPointModel model) {
        waypointRepository.deleteTestData(model);
    }

    public LiveData<List<WayPointModel>> getAllWaypointModels() {
        return liveDataWaypoint;
    }

    public void insertMultimediaModel(MultimediaModel model) {
        multimediaRepository.insertMultiMedia(model);
    }

    public void updateMultimediaModel(MultimediaModel model) {
        multimediaRepository.updateTestData(model);
    }

    public void deleteMultimediaModel(MultimediaModel model) {
        multimediaRepository.deleteTestData(model);
    }

    public LiveData<List<MultimediaModel>> getAllMultimediaModels() {
        return liveDataMedia;
    }
}
