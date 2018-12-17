package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "ROUTE_MODEL")
public class RouteModel {
    private List<Integer> route;
    @NonNull
    @PrimaryKey
    private String name;
    private boolean done;
    private String resourceID;

    public RouteModel(List<Integer> route, String name, boolean done, String resourceID) {
        this.route = route;
        this.name = name;
        this.done = done;
        this.resourceID = resourceID;
    }

    public List<Integer> getRoute() {
        return route;
    }

    public void setRoute(List<Integer> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    @Ignore
    @Override
    public String toString() {
        return "RouteModel{" +
                "route=" + route +
                ", name='" + name + '\'' +
                ", done=" + done +
                ", resourceID='" + resourceID + '\'' +
                '}';
    }
}