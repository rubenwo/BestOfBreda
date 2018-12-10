package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

public class DetailedViewModel extends AndroidViewModel {
    WayPointModel wayPoint;

    public DetailedViewModel(@NonNull Application application) {
        super(application);
    }

    public WayPointModel getWayPoint(){
        return wayPoint;
    }
}
