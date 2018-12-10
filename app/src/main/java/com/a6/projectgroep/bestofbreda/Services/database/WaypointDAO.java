package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

import java.util.List;

@Dao
public interface WaypointDAO {
    @Query("SELECT * FROM waypoint_model")
    LiveData<List<WayPointModel>> getAllWaypoints();

    @Query("SELECT * FROM waypoint_model WHERE waypointID = :id")
    LiveData<WayPointModel> getLiveWaypoint(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWaypoint(WayPointModel wayPoint);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWaypoint(WayPointModel wayPointModel);

    @Delete()
    void deleteWayPoint(WayPointModel model);
}
