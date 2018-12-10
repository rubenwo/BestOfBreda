package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "ROUTE_MODEL")
public class RouteModel {
    private ArrayList<Integer> route;
    @NonNull
    @PrimaryKey
    private String name;
    private boolean done;

    public RouteModel(ArrayList<Integer> route, String name, boolean done) {
        this.route = route;
        this.name = name;
        this.done = done;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Integer> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }
}
