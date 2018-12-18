package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

public class VolleyConnection {
    private static VolleyConnection volleyConnection;
    private RequestQueue queue;

    private VolleyConnection(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static VolleyConnection getInstance(Context context) {
        if (volleyConnection == null) {
            volleyConnection = new VolleyConnection(context);
        }
        return volleyConnection;
    }

    public void getRoute(List<LatLng> waypoints, Response.Listener<JSONObject> onSuccess, Response.ErrorListener onError) {
        String url = getRouteURL(waypoints);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.i("route", "OK");
                    try {
                        System.out.println(response);
                        JSONArray jsonArray = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
                        for (int idx = 0; idx < jsonArray.length(); idx++) {
                            LatLng latLng = new LatLng(jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lat"), jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lng"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.i("route", "NOK"));
        queue.add(request);
    }

    public String getRouteURL(List<LatLng> waypoints) {
        // Origin, destination and waypoints of route
        String str_origin = "origin=" + waypoints.get(0).latitude + "," + waypoints.get(0).longitude;
        String str_dest = "destination=" + waypoints.get(waypoints.size() - 1).latitude + "," + waypoints.get(waypoints.size() - 1).longitude;
        String str_waypoint = "waypoints=";

        for (int i = 1; i < waypoints.size() - 2; i++) {
            str_waypoint += "via:" + waypoints.get(i).latitude + "," + waypoints.get(i).longitude + "|";
        }

        str_waypoint += "via:" + waypoints.get(waypoints.size() - 2).latitude + "," + waypoints.get(waypoints.size() - 2).longitude;

        String trafficMode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + trafficMode + "&" + str_waypoint;

        // Building the url to the web service
        String url3 = "https://maps.moviecast.io/directions?" + parameters + "&key=6f11e342-bdef-434d-98c6-205098f327e9";
        return url3;
    }
}
