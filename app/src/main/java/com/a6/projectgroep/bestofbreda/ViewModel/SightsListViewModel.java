package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;

import java.util.List;

public class SightsListViewModel extends AndroidViewModel {

    public SightsListViewModel(@NonNull Application application) {
        super(application);
    }
}