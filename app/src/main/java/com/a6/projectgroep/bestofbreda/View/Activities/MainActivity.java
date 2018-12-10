package com.a6.projectgroep.bestofbreda.View.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MainViewModel mainViewModel;
    private static final int GPS_REQUEST = 50;
    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        mainViewModel = new MainViewModel(getApplication());

        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case GPS_REQUEST :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //DOE IETS
                } else {
                    System.out.println("geen locatie");
                    finish();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        List<WayPointModel> markers = mainViewModel.getAllWaypointModels().getValue();

        for (WayPointModel model: markers) {
            GeoCoderService.getInstance(getApplication()).placeMarker(mMap, model.getLocation(), 222, model.getName(), model.getDescription());
        }
        mainViewModel.getRoute(markers.get(0).getLocation(), markers.get(1).getLocation());
    }
}
