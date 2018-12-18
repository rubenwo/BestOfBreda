package com.a6.projectgroep.bestofbreda;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.database.NavigationDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestOfBreda extends Application {
    public static final String CHANNEL_SIGHT_PASSED_ID = "sightPassed";

    @Override
    public void onCreate() {
        super.onCreate();

        List<WaypointModel> testWayPoints = new ArrayList<>();
        List<String> strings = Arrays.asList("Test", "Test2");
        String string = "testString";
        MultimediaModel model = new MultimediaModel(strings, string);
        testWayPoints.add(new WaypointModel("avans breda", "avans hogeschool", GeoCoderService.getInstance(this).getLocationFromName("Avans breda"), false, false, model));
        testWayPoints.add(new WaypointModel("casino", "Holland Casino Breda", GeoCoderService.getInstance(this).getLocationFromName("holland casino breda"), false, false, model));
        testWayPoints.add(new WaypointModel("station breda", "Centraal Station Breda", GeoCoderService.getInstance(this).getLocationFromName("station breda"), false, false, model));

//        new Thread(() -> {
//            NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(0));
//            NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(1));
//            NavigationDatabase.getInstance(this).waypointDAO().insertWaypoint(testWayPoints.get(2));
//        }).start();


//        ArrayList<String> stringRoutes = new ArrayList<>();
//        stringRoutes.add("test");
//        RouteModel testModel = new RouteModel(stringRoutes, "testRoute", false, "route_blindwalls");
//        new Thread(() -> NavigationDatabase.getInstance(this).routeDAO().insertRoute(testModel)).start();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sightChannel = new NotificationChannel(
                    CHANNEL_SIGHT_PASSED_ID,
                    "Nearby sight",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sightChannel.setDescription("the notification appears when a user is near a sight with the tour running");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sightChannel);
        }
    }
}
