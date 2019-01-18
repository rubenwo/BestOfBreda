package com.a6.projectgroep.bestofbreda.Services;

import com.google.android.gms.maps.model.LatLng;

public interface RouteReceivedListener {

    void onRoutePointReceived(LatLng latLng);
}
