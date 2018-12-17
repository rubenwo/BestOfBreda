package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "WAYPOINT_MODEL", primaryKeys = {"waypoint_id"})
public class WaypointModel {
    @ColumnInfo(name = "waypoint_id")
    @NonNull
    private int id;
    @ColumnInfo(name = "waypoint_name")
    private String name;
    @ColumnInfo(name = "waypoint_description")
    private String description;
    @ColumnInfo(name = "waypoint_location")
    private LatLng location;
    @ColumnInfo(name = "waypoint_already_seen")
    private boolean alreadySeen;
    @ColumnInfo(name = "waypoint_is_favorite")
    private boolean isFavorite;
    @ColumnInfo(name = "waypoint_multimedia_id")
    private int multimediaID;

    public WaypointModel(@NonNull int id, String name, String description, LatLng location, boolean alreadySeen, boolean isFavorite, int multimediaID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.alreadySeen = alreadySeen;
        this.isFavorite = isFavorite;
        this.multimediaID = multimediaID;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public boolean isAlreadySeen() {
        return alreadySeen;
    }

    public void setAlreadySeen(boolean alreadySeen) {
        this.alreadySeen = alreadySeen;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getMultimediaID() {
        return multimediaID;
    }

    public void setMultimediaID(int multimediaID) {
        this.multimediaID = multimediaID;
    }

    @Override
    public String toString() {
        return "WaypointModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", alreadySeen=" + alreadySeen +
                ", isFavorite=" + isFavorite +
                ", multimediaID=" + multimediaID +
                '}';
    }
}
