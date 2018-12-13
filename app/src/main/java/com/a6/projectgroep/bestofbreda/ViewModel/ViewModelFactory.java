package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Services.LiveLocationListener;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private LiveLocationListener locationListener;

    public ViewModelFactory(Application application, LiveLocationListener locationListener) {
        this.application = application;
        this.locationListener = locationListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(application, locationListener);
    }
}
