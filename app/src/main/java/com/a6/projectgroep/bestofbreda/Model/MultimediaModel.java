package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "MULTIMEDIA_MODEL")
public class MultimediaModel {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "picture_urls")
    private ArrayList<String> pictureUrls;
    @ColumnInfo(name = "video_urls")
    private ArrayList<String> videoUrls;

    public MultimediaModel(ArrayList<String> pictureUrls, ArrayList<String> videoUrls) {
        this.pictureUrls = pictureUrls;
        this.videoUrls = videoUrls;
    }

    public ArrayList<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(ArrayList<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public ArrayList<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(ArrayList<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @NonNull
    public int getId() {
        return id;
    }
}
