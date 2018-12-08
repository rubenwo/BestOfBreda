package com.a6.projectgroep.bestofbreda.Model;

import java.util.ArrayList;

public class MultimediaModel {
    private ArrayList<String> pictureUrls;
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
}
