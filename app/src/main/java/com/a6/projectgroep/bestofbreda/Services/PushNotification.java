package com.a6.projectgroep.bestofbreda.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Activities.DetailedActivity;

public class PushNotification {
    private NotificationManagerCompat managerCompat;
    private static PushNotification instance;
    public static final String CHANNEL_SIGHT_PASSED_ID = "sightPassed";

    private PushNotification(Context context)
    {
        managerCompat = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sightChannel = new NotificationChannel(
                    CHANNEL_SIGHT_PASSED_ID,
                    "Nearby sight",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sightChannel.setDescription("the notification appears when a user is near a sight with the tour running");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sightChannel);
        }
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
        Intent resultIntent = new Intent(context, DetailedActivity.class);
        resultIntent.putExtra("SightName", sightName);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_SIGHT_PASSED_ID)
                .setSmallIcon(R.drawable.ic_nav_sights)
                .setContentTitle(sightName)
                .setContentText(sightDescription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)
                .build();

        managerCompat.notify(1, notification);
    }
}
