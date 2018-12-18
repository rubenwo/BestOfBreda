package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.util.Log;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.database.RouteDAO;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointDAO;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDecoder {
    private static final String TAG = "JSONDECODER_TAG";

    public static boolean decodeJsonWaypointsFile(Context context, String path, WaypointDAO dao) {
        String json;
        try {
            InputStream is = context.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        Log.d(TAG, path);
        Log.d(TAG, json);

        try {
            JSONArray array = new JSONArray(json);
            for (int idx = 0; idx < array.length(); idx++) {
                JSONObject obj = array.getJSONObject(idx);
                double latitude = obj.getDouble("latitude");
                double longitude = obj.getDouble("longitude");
                String videoUrl = null;
                if (!obj.isNull("videoUrl"))
                    videoUrl = obj.getString("videoUrl");

                String name, descNL, descEN;
                name = obj.getJSONObject("title").getString("nl");
                descNL = obj.getJSONObject("description").getString("nl");
                descEN = obj.getJSONObject("description").getString("en");
                JSONArray images = obj.getJSONArray("images");
                ArrayList<String> urls = new ArrayList<>();
                ArrayList<String> files = new ArrayList<>();
                for (int index = 0; index < images.length(); index++) {
                    urls.add(images.getJSONObject(index).getString("url"));
                    files.add(images.getJSONObject(index).getString("file"));
                }
                dao.insertWaypoint(new WaypointModel(name, descNL, descEN, new LatLng(latitude, longitude), false, false, new MultimediaModel(urls, videoUrl)));
            }
            return true;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static boolean decodeJsonRouteFile(Context context, String path, RouteDAO dao) {
        String json;
        try {
            InputStream is = context.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        try {
            JSONObject routeObject = new JSONObject(json);
            String name = routeObject.getString("name");
            String picturePath = routeObject.getString("picture");
            List<String> points = new ArrayList<>();
            JSONArray pointsArray = new JSONArray("points");
            for (int i = 0; i < pointsArray.length(); i++) {
                points.add(pointsArray.getString(i));
            }
            dao.insertRoute(new RouteModel(points, name, false, picturePath));
            return true;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
