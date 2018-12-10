package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;

import com.a6.projectgroep.bestofbreda.Services.database.RouteDAO;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointDAO;

import java.util.concurrent.ThreadPoolExecutor;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private ThreadPoolExecutor backgorundQueryPool;

    private DatabaseHandler(Context context) {
    }

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context);
        return instance;
    }

    public RouteDAO routeDAO() {
        return null;
    }

    public WaypointDAO waypointDAO() {
        return null;
    }


    public void PerformQueryOnBackground(Runnable task) {

    }

}
