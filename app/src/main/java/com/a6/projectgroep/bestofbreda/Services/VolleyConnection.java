package com.a6.projectgroep.bestofbreda.Services;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;

public class VolleyConnection {

    RequestQueue queue;
    public static VolleyConnection volleyConnection;

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

    public String getRouteURL(LatLng origin, LatLng dest)
    {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;       // Detination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //String trafficMode = "mode=walking";
        // trafficMode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        String url2 = "https://maps.moviecast.io/directions?origin=Disneyland&destination=Universal+Studios+Hollywood&key=YOUR_API_KEY";
        String url3 = "https://maps.moviecast.io/directions?" + parameters;
        return url3 + "&key=6f11e342-bdef-434d-98c6-205098f327e9";
    }

    public ArrayList<LatLng> getRoute(LatLng origin, LatLng dest)
    {
        String url = getRouteURL(origin, dest);
        ArrayList<LatLng> routePoints = new ArrayList<>();
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("route", "OK");
                        try {
                            System.out.println(response);
                            JSONArray jsonArray = response.getJSONArray("routes").getJSONArray(2).getJSONArray(2);
                            for( int idx = 0; idx < jsonArray.length(); idx++) {
                                LatLng latLng = new LatLng(1,1);
//                                String author = response.getJSONObject(idx).getString("author");
//                                String desc = response.getJSONObject(idx).getJSONObject("description").getString("nl");
//                                String desc2 = response.getJSONObject(idx).getJSONObject("description").getString("en");
//                                JSONArray images = response.getJSONObject(idx).getJSONArray("images");
//                                String material = response.getJSONObject(idx).getJSONObject("material").getString("nl");
//                                String adress = response.getJSONObject(idx).getString("address");
//                                String fotograaf = response.getJSONObject(idx).getString("photographer");
//                                int index = new Random().nextInt(images.length());
//
//                                String imageUrl = "https://api.blindwalls.gallery/" +
//                                        images.getJSONObject(index).getString("url");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("route", "NOK");
                    }
                }
        );
        queue.add(request);
        return routePoints;
    }
}
