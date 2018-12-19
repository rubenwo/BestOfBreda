package com.a6.projectgroep.bestofbreda;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.a6.projectgroep.bestofbreda.Services.BackgroundService;

public class ApplicationLifecycle implements LifecycleObserver {
    private Application application;
    private Intent backgroundService;

    public ApplicationLifecycle(Application application) {
        this.application = application;
        backgroundService = new Intent(application, BackgroundService.class);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onEnterForeground() {
        application.stopService(backgroundService);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        application.startService(backgroundService);
    }
}
