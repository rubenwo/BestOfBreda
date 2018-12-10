package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class JsonDecoder {
    public static void DecodeJsonFile(Context context, String path) {
        String json;

        try {
            InputStream is = context.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            JSONArray array = new JSONArray(json);
            String language = Locale.getDefault().getLanguage();
            for (int idx = 0; idx < array.length(); idx++) {
                JSONObject obj = array.getJSONObject(idx);
                double latitude = obj.getDouble("latitude");
                double longitude = obj.getDouble("longitude");
                String videoUrl = obj.getString("videoUrl");
                String name;
                String desc;
                if (language.equals("nl")) {
                    name = obj.getJSONObject("title").getString("nl");
                    desc = obj.getJSONObject("description").getString("nl");
                } else {
                    name = obj.getJSONObject("title").getString("en");
                    desc = obj.getJSONObject("description").getString("en");
                }

                JSONArray images = obj.getJSONArray("images");
                String[] urls = new String[images.length()];
                String[] files = new String[images.length()];
                for (int index = 0; index < images.length(); index++) {
                    urls[index] = images.getJSONObject(index).getString("url");
                    files[index] = images.getJSONObject(index).getString("file");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }
}
