package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;

import com.a6.projectgroep.bestofbreda.Services.database.MultimediaDAO;
import com.a6.projectgroep.bestofbreda.Services.database.RouteDAO;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointDAO;

public class DatabaseService {
    private static volatile DatabaseService instance;
    private Application application;
    private RouteDAO routeDAO;
    private MultimediaDAO multimediaDAO;
    private WaypointDAO waypointDAO;

    private DatabaseService(Application application) {
        this.application = application;
    }

    public static DatabaseService getInstance(Application application) {
        if (instance == null) {
            instance = new DatabaseService(application);
        }
        return instance;
    }
}
