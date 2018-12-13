package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

public class VolleyConnection {

    RequestQueue queue;
    public static VolleyConnection volleyConnection;
    private RouteReceivedListener routeReceivedListener;

    private VolleyConnection(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public static VolleyConnection getInstance(Context context)
    {
        if(volleyConnection == null)
        {
            volleyConnection = new VolleyConnection(context);
        }
        return volleyConnection;
    }

    public String getRouteURL(ArrayList<LatLng> waypoints)
    {
        // Origin, destination and waypoints of route
        String str_origin = "origin=" + waypoints.get(0).latitude + "," + waypoints.get(0).longitude;
        String str_dest = "destination=" + waypoints.get(waypoints.size()-1).latitude + "," + waypoints.get(waypoints.size()-1).longitude;
        String str_waypoint = "waypoints=";
        for(int i = 1; i < waypoints.size()-2; i++)
        {
            str_waypoint += "via:" + waypoints.get(i).latitude + "," + waypoints.get(i).longitude + "|";
        }
        str_waypoint += "via:" + waypoints.get(waypoints.size()-2).latitude + "," + waypoints.get(waypoints.size()-2).longitude;

        String trafficMode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + trafficMode + "&" + str_waypoint;

        // Building the url to the web service
        String url3 = "https://maps.moviecast.io/directions?" + parameters + "&key=6f11e342-bdef-434d-98c6-205098f327e9";
        return url3;
    }

    public void getRoute(ArrayList<LatLng> waypoints, RouteReceivedListener listener)
    {
        String url = getRouteURL(waypoints);
        routeReceivedListener = listener;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("route", "OK");
                        try {
                            System.out.println(response);
                            JSONArray jsonArray = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
                            for (int idx = 0; idx < jsonArray.length(); idx++) {
                                LatLng latLng = new LatLng(jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lat"), jsonArray.getJSONObject(idx).getJSONObject("end_location").getDouble("lng"));
                                routeReceivedListener.onRoutePointReceived(latLng);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("route", "NOK");
            }
        });
        queue.add(request);
    }
}
