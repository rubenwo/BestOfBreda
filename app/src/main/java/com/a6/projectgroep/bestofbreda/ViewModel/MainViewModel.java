package com.a6.projectgroep.bestofbreda.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.Services.LiveLocationListener;
import com.a6.projectgroep.bestofbreda.Services.LocationHandler;
import com.a6.projectgroep.bestofbreda.Services.RouteReceivedListener;
import com.a6.projectgroep.bestofbreda.Services.VolleyConnection;
import com.a6.projectgroep.bestofbreda.Services.database.MultimediaRepository;
import com.a6.projectgroep.bestofbreda.Services.database.RouteRepository;
import com.a6.projectgroep.bestofbreda.Services.database.WaypointRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public GoogleMapsAPIManager mapsApiManager;
    public VolleyConnection volleyConnection;
    private WaypointRepository waypointRepository;
    private RouteRepository routeRepository;
    private MultimediaRepository multimediaRepository;
    private LiveData<List<WaypointModel>> mWaypointModels;
    private LiveData<List<RouteModel>> mRouteModels;
    private LiveData<List<MultimediaModel>> mMultiMediaModels;
    private LiveData<List<WaypointModel>> liveData;

    public MainViewModel(@NonNull Application application, LiveLocationListener listener) {
        super(application);
        mapsApiManager = GoogleMapsAPIManager.getInstance(application, listener);
        waypointRepository = new WaypointRepository(application);
        routeRepository = new RouteRepository(application);
        multimediaRepository = new MultimediaRepository(application);
        mWaypointModels = waypointRepository.getAllWaypoints();
        mRouteModels = routeRepository.getAllRouteModels();
        mMultiMediaModels = multimediaRepository.getAllMultiMedia();
    }

    //region waypoint
    public void insertWayPointModel(WaypointModel model) {
        waypointRepository.insertWaypoint(model);
    }

    public void updateWayPointModel(WaypointModel model) {
        waypointRepository.updateWaypoint(model);
    }

    public void deleteWayPointModel(WaypointModel model) {
        waypointRepository.deleteWaypoint(model);
    }

    public LiveData<WaypointModel> getWaypointModelByName(String name) {
        return waypointRepository.getWaypointByName(name);
    }

    public LiveData<List<WaypointModel>> getAllWaypointModelsByNames(List<String> waypointNames) {
        return waypointRepository.getAllWaypointModelsByNames(waypointNames);
    }

    public LiveData<List<WaypointModel>> getAllWaypointModels() {
        return mWaypointModels;
    }

    //endregion
    //region route
    public void insertRouteModel(RouteModel model) {
        routeRepository.insertRouteModel(model);
    }

    public void updateRouteModel(RouteModel model) {
        routeRepository.updateRouteModel(model);
    }

    public void deleteRouteModel(RouteModel model) {
        routeRepository.deleteRouteModel(model);
    }

    public LiveData<RouteModel> getRouteByName(String routeName) {
        return routeRepository.getRouteModel(routeName);
    }

    public LiveData<List<RouteModel>> getAllRouteModels() {
        return mRouteModels;
    }

    //endregion
    //region multimedia
    public void insertMultiMediaModel(MultimediaModel model) {
        multimediaRepository.insertMultiMedia(model);
    }

    public void updateMultiMediaModel(MultimediaModel model) {
        multimediaRepository.updateMultiMedia(model);
    }

    public void deleteMultiMediaModel(MultimediaModel model) {
        multimediaRepository.deleteMultiMedia(model);
    }

    public LiveData<List<MultimediaModel>> getAllMultiMediaModels() {
        return mMultiMediaModels;
    }

    //endregion


    public void getRoutePoints(ArrayList<LatLng> waypoints, RouteReceivedListener listener) {
        volleyConnection = VolleyConnection.getInstance(getApplication().getApplicationContext());
        //    volleyConnection.getRoute(waypoints, listener);
    }

    public LatLng getCurrentPosition() {
        return LocationHandler.getInstance(getApplication()).getCurrentLocation();
    }

    public void createWaypointModelList() {
        liveData = Transformations.switchMap(getRouteByName("nameOfRoute"), routeModel -> {
            List<WaypointModel> waypoints = new ArrayList<>();
            Log.i("ROUTEMODEL", routeModel.toString());
            for (String w : routeModel.getRoute()) {
                getWaypointModelByName(w).observe(getApplication(), waypointModel -> {
                    waypoints.add(waypointModel);
                });
            }
            return liveData;
        });
    }

    public LiveData<List<WaypointModel>> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
}
