package com.a6.projectgroep.bestofbreda.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "MULTIMEDIA_MODEL")
public class MultimediaModel {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<String> pictureUrls;
    private String videoUrls;

    public MultimediaModel(List<String> pictureUrls, String videoUrls) {
        this.pictureUrls = pictureUrls;
        this.videoUrls = videoUrls;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @Ignore
    @Override
    public String toString() {
        return "MultimediaModel{" +
                "id=" + id +
                ", pictureUrls=" + pictureUrls +
                ", videoUrls='" + videoUrls + '\'' +
                '}';
    }
}
