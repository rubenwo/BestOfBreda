package com.a6.projectgroep.bestofbreda.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import com.a6.projectgroep.bestofbreda.R;

import static com.a6.projectgroep.bestofbreda.BestOfBreda.CHANNEL_SIGHT_PASSED_ID;

public class PushNotification {
    private NotificationManagerCompat managerCompat;
    private static PushNotification instance;

    private PushNotification(Context context)
    {
        managerCompat = NotificationManagerCompat.from(context);
    }

    // currently used Singleton, but can be changed
    public static PushNotification getInstance(Context context)
    {
        if (instance == null)
            instance = new PushNotification(context);
        return instance;
    }

    public void SendSightNotification(View v, String sightName, String sightDescription, Context context) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_SIGHT_PASSED_ID)
                .setSmallIcon(R.drawable.ic_nav_sights)
                .setContentTitle(sightName)
                .setContentText(sightDescription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        managerCompat.notify(1, notification);
    }

    public void SendSightNotification(String sightName, String sightDescription, Context context) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_SIGHT_PASSED_ID)
                .setSmallIcon(R.drawable.ic_nav_sights)
                .setContentTitle(sightName)
                .setContentText(sightDescription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        managerCompat.notify(1, notification);
    }
}
