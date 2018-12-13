package com.a6.projectgroep.bestofbreda.Services;

import com.google.android.gms.maps.model.LatLng;

public interface RouteReceivedListener {

    public void onRoutePointReceived(LatLng latLng);
}
