package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.List;

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
        Log.d("VolleyConnection", url);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                onSuccess,
                onError);
        queue.add(request);
    }

    public String getRouteURL(List<LatLng> waypoints) {
        // Origin, destination and waypoints of route
        String str_origin = "origin=" + waypoints.get(0).latitude + "," + waypoints.get(0).longitude;
        String str_dest = "destination=" + waypoints.get(waypoints.size() - 1).latitude + "," + waypoints.get(waypoints.size() - 1).longitude;
        String str_waypoint = "waypoints=optimize:true";

        for (int i = 1; i < waypoints.size() - 1; i++) {
            str_waypoint += "|" + waypoints.get(i).latitude + "," + waypoints.get(i).longitude;
        }

        str_waypoint += "|" + waypoints.get(waypoints.size() - 1).latitude + "," + waypoints.get(waypoints.size() - 2).longitude;

        String trafficMode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + trafficMode + "&" + str_waypoint;

        // Building the url to the web service
        String url3 = "https://maps.moviecast.io/directions?" + parameters + "&key=6f11e342-bdef-434d-98c6-205098f327e9";
        return url3;
    }
}
