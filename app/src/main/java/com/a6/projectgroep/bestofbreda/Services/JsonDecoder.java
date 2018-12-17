package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.database.RouteRepository;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JsonDecoder {
    public static boolean decodeJsonWaypointsFile(Application application, String path) {
        String json;
        ArrayList<WaypointModel> wayPointModels = new ArrayList<>();
        try {
            InputStream is = application.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        WaypointRepository repository = new WaypointRepository(application);
        try {
            JSONArray array = new JSONArray(json);
            String language = Locale.getDefault().getLanguage();
            for (int idx = 0; idx < array.length(); idx++) {
                JSONObject obj = array.getJSONObject(idx);
                double latitude = obj.getDouble("latitude");
                double longitude = obj.getDouble("longitude");
                String videoUrl = null;
                if (!obj.isNull("videoUrl"))
                    videoUrl = obj.getString("videoUrl");

                String name, desc;
                if (language.equals("nl")) {
                    name = obj.getJSONObject("title").getString("nl");
                    desc = obj.getJSONObject("description").getString("nl");
                } else {
                    name = obj.getJSONObject("title").getString("en");
                    desc = obj.getJSONObject("description").getString("en");
                }

                JSONArray images = obj.getJSONArray("images");
                ArrayList<String> urls = new ArrayList<>();
                ArrayList<String> files = new ArrayList<>();
                for (int index = 0; index < images.length(); index++) {
                    urls.add(images.getJSONObject(index).getString("url"));
                    files.add(images.getJSONObject(index).getString("file"));
                }
                //TODO: Change ID's to something better
                // repository.insertWaypoint(new WaypointModel(1, name, desc, new LatLng(latitude, longitude), false, false, 1));
                repository.insertWaypoint(new WaypointModel(name, desc, new LatLng(latitude, longitude), false, false, new MultimediaModel(urls, videoUrl)));
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean decodeJsonRouteFile(Application application, String path) {
        String json;
        try {
            InputStream is = application.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        RouteRepository repository = new RouteRepository(application);
        try {
            JSONObject routeObject = new JSONObject(json);
            String name = routeObject.getString("name");
            List<String> points = new ArrayList<>();
            JSONArray pointsArray = new JSONArray("points");
            for (int i = 0; i < pointsArray.length(); i++) {
                points.add(pointsArray.getString(i));
            }
            // repository.insertRouteModel(new RouteModel(, , ));
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
