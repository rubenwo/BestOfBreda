package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.database.MultimediaRepository;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;

import java.util.List;

public class DetailedViewModel extends AndroidViewModel {
    private GoogleMapsAPIManager manager;

    private WaypointRepository waypointRepository;
    private LiveData<List<WaypointModel>> liveDataWaypoint;
    private MultimediaRepository multimediaRepository;
    private LiveData<List<MultimediaModel>> liveDataMedia;

    public DetailedViewModel(@NonNull Application application) {
        super(application);
        manager = GoogleMapsAPIManager.getInstance(application);
        waypointRepository = new WaypointRepository(application);
        liveDataWaypoint = waypointRepository.getAllWaypoints();
        multimediaRepository = new MultimediaRepository(application);
        liveDataMedia = multimediaRepository.getAllMultiMedia();
    }

    public void insertWayPointModel(WaypointModel model) {
        waypointRepository.insertWaypoint(model);
    }

    public void updateWayPointModel(WaypointModel model) {
        waypointRepository.updateWaypoint(model);
    }

    public void deleteWayPointModel(WaypointModel model) {
        waypointRepository.deleteWaypoint(model);
    }

    public LiveData<List<WaypointModel>> getAllWaypointModels() {
        return manager.getAvailableWayPoints();
    }

    public void insertMultimediaModel(MultimediaModel model) {
        multimediaRepository.insertMultiMedia(model);
    }

    public void updateMultimediaModel(MultimediaModel model) {
        multimediaRepository.updateMultiMedia(model);
    }

    public void deleteMultimediaModel(MultimediaModel model) {
        multimediaRepository.deleteMultiMedia(model);
    }

    public LiveData<List<MultimediaModel>> getAllMultimediaModels() {
        return liveDataMedia;
    }
}
