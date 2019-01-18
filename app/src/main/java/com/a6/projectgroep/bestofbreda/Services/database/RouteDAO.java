package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;

import java.util.List;

@Dao
public interface RouteDAO {
    @Query("SELECT * FROM route_model")
    LiveData<List<RouteModel>> getAllRoutes();

    @Query("SELECT * FROM route_model WHERE name LIKE :routeName")
    LiveData<RouteModel> getLiveRoute(String routeName);

    @Insert
    void insertRoute(RouteModel route);

    @Update
    void updateRoute(RouteModel route);

    @Delete
    void deleteRoute(RouteModel route);
}
