package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;

import java.util.List;

@Dao
public interface WaypointDAO {
    @Insert
    void insertWaypoint(WaypointModel model);

    @Update
    void updateWaypoint(WaypointModel model);

    @Delete
    void deleteWaypoint(WaypointModel model);

    @Query("SELECT * FROM WAYPOINT_MODEL")
    LiveData<List<WaypointModel>> getAllWayPoints();

    @Query("SELECT * FROM WAYPOINT_MODEL WHERE waypoint_name IN (:waypointNames)")
    LiveData<List<WaypointModel>> getAllWaypointModelsFromNames(List<String> waypointNames);

    @Query("SELECT * FROM WAYPOINT_MODEL WHERE waypoint_name = :waypointName")
    LiveData<WaypointModel> getWaypointModelByName(String waypointName);
}
