package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "MULTIMEDIA_MODEL")
public class MultimediaModel {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "picture_urls")
    private List<String> pictureUrls;
    @ColumnInfo(name = "video_urls")
    private List<String> videoUrls;

    public MultimediaModel(List<String> pictureUrls, List<String> videoUrls) {
        this.pictureUrls = pictureUrls;
        this.videoUrls = videoUrls;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
