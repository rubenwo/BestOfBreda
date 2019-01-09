package com.a6.projectgroep.bestofbreda.Services;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class GeoCoderService {
    private static volatile GeoCoderService instance;
    private Application application;
    private Geocoder geocoder;

    private GeoCoderService(Application application) {
        this.application = application;
        geocoder = new Geocoder(application);
    }

    public static GeoCoderService getInstance(Application application) {
        if (instance == null) {
            instance = new GeoCoderService(application);
        }
        return instance;
    }

    /**
     * Returns the LatLng for a given location.
     *
     * @param name
     * @return
     */
    public LatLng getLocationFromName(String name) {
        LatLng location = null;
        try {
            Address address = geocoder.getFromLocationName(name, 1).get(0);
            location = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * places a marker in the specified color at the given location. Title and description are not mandatory.
     *
     * @param location
     * @param markerColor
     * @param title
     * @param description
     */
    public Marker placeMarker(@NonNull GoogleMap map, @NonNull LatLng location, @NonNull float markerColor, @Nullable String title, @Nullable String description) {
        Marker marker;
        if (title == null && description != null) {
            marker = map.addMarker(new MarkerOptions().position(location));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor));
            try {
                Log.e("MarkerError", "No title so this description won't be displayed!");
                throw new Exception("No title so this description won't be displayed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (title != null && description == null) {
            marker = map.addMarker(new MarkerOptions().position(location).title(title));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor));
        }
        if (title == null && description == null) {
            marker = map.addMarker(new MarkerOptions().position(location));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor));
        } else {
            marker = map.addMarker(new MarkerOptions().position(location).title(title).snippet(description));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor));
        }
        return marker;
    }
}
