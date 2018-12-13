package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class JsonDecoder {
//    public static ArrayList<WaypointModel> DecodeJsonFile(Context context, String path) {
//        String json;
//        ArrayList<WaypointModel> wayPointModels = new ArrayList<>();
//        try {
//            InputStream is = context.getAssets().open(path);
//
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            json = new String(buffer, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        try {
//            JSONArray array = new JSONArray(json);
//            String language = Locale.getDefault().getLanguage();
//            for (int idx = 0; idx < array.length(); idx++) {
//                JSONObject obj = array.getJSONObject(idx);
//                double latitude = obj.getDouble("latitude");
//                double longitude = obj.getDouble("longitude");
//                String videoUrl = null;
//                if(!obj.isNull("videoUrl"))
//                    videoUrl = obj.getString("videoUrl");
//
//                String name, desc;
//                if (language.equals("nl")) {
//                    name = obj.getJSONObject("title").getString("nl");
//                    desc = obj.getJSONObject("description").getString("nl");
//                } else {
//                    name = obj.getJSONObject("title").getString("en");
//                    desc = obj.getJSONObject("description").getString("en");
//                }
//
//                JSONArray images = obj.getJSONArray("images");
//                String[] urls = new String[images.length()];
//                String[] files = new String[images.length()];
//                for (int index = 0; index < images.length(); index++) {
//                    urls[index] = images.getJSONObject(index).getString("url");
//                    files[index] = images.getJSONObject(index).getString("file");
//                }
//
//                MultimediaModel multimedia = new MultimediaModel(Arrays.asList(urls),videoUrl );
//                LatLng latLng = new LatLng(latitude, longitude);
//                WaypointModel wayPoint = new WaypointModel(name, latLng, desc, false, false, multimedia);
//                wayPointModels.add(wayPoint);
//            }
//            return wayPointModels;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return wayPointModels;
//        }
//    }
    //TODO: veranderingen aan de entities aanpassen in de decoder.
}
