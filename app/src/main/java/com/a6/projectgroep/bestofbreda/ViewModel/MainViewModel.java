package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;

public class MainViewModel extends AndroidViewModel {
    public GoogleMapsAPIManager mapsApiManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mapsApiManager = GoogleMapsAPIManager.getInstance(application.getApplicationContext());
    }
}
