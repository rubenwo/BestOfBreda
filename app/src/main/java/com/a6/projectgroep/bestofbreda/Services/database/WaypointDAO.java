package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

import java.util.ArrayList;

public interface WaypointDAO {
    ArrayList<WayPointModel> getAllWaypoints();

    LiveData<WayPointModel> getLiveWaypoint(int id);

    WayPointModel getWayPoint(int id);

    void insertWaypoint(WayPointModel wayPoint);
}
