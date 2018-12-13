package com.a6.projectgroep.bestofbreda;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BestOfBreda extends Application {
    public static final String CHANNEL_SIGHT_PASSED_ID = "sightPassed";

    @Override
    public void onCreate() {
        super.onCreate();

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
