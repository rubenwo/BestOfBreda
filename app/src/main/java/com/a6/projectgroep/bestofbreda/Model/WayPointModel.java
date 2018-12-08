package com.a6.projectgroep.bestofbreda.Model;

import com.google.android.gms.maps.model.LatLng;

public class WayPointModel {

    private String name;
    private LatLng location;
    private String description;
    private int height;
    private boolean alreadySeen;
    private boolean isFavourite;
    private MultimediaModel multimedia;

    public WayPointModel(String name, LatLng location, String description, int height, boolean alreadySeen, boolean isFavourite, MultimediaModel multimedia) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.height = height;
        this.alreadySeen = alreadySeen;
        this.isFavourite = isFavourite;
        this.multimedia = multimedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAlreadySeen() {
        return alreadySeen;
    }

    public void setAlreadySeen(boolean alreadySeen) {
        this.alreadySeen = alreadySeen;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public MultimediaModel getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(MultimediaModel multimedia) {
        this.multimedia = multimedia;
    }
}
