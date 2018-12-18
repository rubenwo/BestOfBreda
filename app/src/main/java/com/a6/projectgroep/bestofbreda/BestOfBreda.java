package com.a6.projectgroep.bestofbreda;

import android.app.Application;
import android.arch.lifecycle.ProcessLifecycleOwner;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestOfBreda extends Application {
    public static final String CHANNEL_SIGHT_PASSED_ID = "sightPassed";
    private ApplicationLifecycle appLifecycle;

    @Override
    public void onCreate() {
        super.onCreate();
        appLifecycle = new ApplicationLifecycle(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycle);

        List<WaypointModel> testWayPoints = new ArrayList<>();
        List<String> strings = Arrays.asList("Test", "Test2");
        String string = "testString";
        MultimediaModel model = new MultimediaModel(strings, string);
        //   testWayPoints.add(new WaypointModel("avans breda", "avans hogeschool", GeoCoderService.getInstance(this).getLocationFromName("Avans breda"), false, false, model));
        //    testWayPoints.add(new WaypointModel("casino", "Holland Casino Breda", GeoCoderService.getInstance(this).getLocationFromName("holland casino breda"), false, false, model));
        //   testWayPoints.add(new WaypointModel("station breda", "Centraal Station Breda", GeoCoderService.getInstance(this).getLocationFromName("station breda"), false, false, model));

    //        new Thread(() -> {
        //           NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(0));
//            NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(1));
//            NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(2));
//            NavigationDatabase.getInstance(this).routeDAO().insertRoute(new RouteModel(Arrays.asList("Avans", "Casino"), "nameOfRoute", false, "resource"));
//        }).start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ProcessLifecycleOwner.get().getLifecycle().removeObserver(appLifecycle);
    }
}

