package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

import java.util.List;

public class SightsListViewModel extends AndroidViewModel {
    private LiveData<List<WayPointModel>> liveData;

    public SightsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<WayPointModel>> getLiveWaypoints(){
        return liveData;
    }

    public WayPointModel getWayPoint(int pointPos){
        if(liveData.getValue().size() >= pointPos)
            return liveData.getValue().get(pointPos);
        else
            return null;
    }
}