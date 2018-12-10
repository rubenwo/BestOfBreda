package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.persistence.room.TypeConverter;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    @TypeConverter
    public static LatLng toLatLng(String object) {
        Gson gson = new Gson();
        return gson.fromJson(object, LatLng.class);
    }

    @TypeConverter
    public static String fromLatLng(LatLng location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }

    @TypeConverter
    public static MultimediaModel toMultiMediaModel(String object) {
        Gson gson = new Gson();
        return gson.fromJson(object, MultimediaModel.class);
    }

    @TypeConverter
    public static String fromMultiMediaModel(MultimediaModel model) {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    @TypeConverter
    public static List<String> stringToStringList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stringListToString(List<String> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static List<Integer> stringToIntegerList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String IntegerListToString(List<Integer> integers) {
        Gson gson = new Gson();
        return gson.toJson(integers);
    }
}
