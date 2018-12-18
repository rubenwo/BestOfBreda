package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity(tableName = "WAYPOINT_MODEL")
public class WaypointModel implements Serializable {
    @PrimaryKey
    @NonNull
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
    private MultimediaModel multiMediaModel;

    public WaypointModel(@NonNull String name, String description, LatLng location, boolean alreadySeen, boolean isFavorite, MultimediaModel multiMediaModel) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.alreadySeen = alreadySeen;
        this.isFavorite = isFavorite;
        this.multiMediaModel = multiMediaModel;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
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

    public MultimediaModel getMultiMediaModel() {
        return multiMediaModel;
    }

    public void setMultiMediaModel(MultimediaModel multiMediaModel) {
        this.multiMediaModel = multiMediaModel;
    }

    @Ignore
    @Override
    public String toString() {
        return "WaypointModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", alreadySeen=" + alreadySeen +
                ", isFavorite=" + isFavorite +
                ", multiMediaModel=" + multiMediaModel +
                '}';
    }
}
