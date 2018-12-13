package com.a6.projectgroep.bestofbreda.View.Activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.BackgroundService;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.LiveLocationListener;
import com.a6.projectgroep.bestofbreda.Services.LocationHandler;
import com.a6.projectgroep.bestofbreda.Services.RouteReceivedListener;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, RouteReceivedListener, LiveLocationListener, GoogleMap.OnInfoWindowClickListener {
    private static final int GPS_REQUEST = 50;
    private MainViewModel mainViewModel;
    private DrawerLayout drawerLayout;
    private MapView mMapView;
    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private List<WayPointModel> markers;
    private ArrayList<LatLng> waypoints;
    private LocationHandler locationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        locationHandler = LocationHandler.getInstance(getApplication(), this);
        mainViewModel.getAllWaypointModels().observe(this, new Observer<List<WayPointModel>>() {
            @Override
            public void onChanged(@Nullable List<WayPointModel> wayPointModels) {
                Toast.makeText(getApplicationContext(), "onChanged", Toast.LENGTH_LONG).show();
            }
        });
        Toolbar toolbar = findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_nav);
        actionBar.setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.mainactivity_drawer_layout);

        NavigationView navigationView = findViewById(R.id.mainactivity_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                //TODO navigate to the activities
                switch (menuItem.getItemId()) {
                    case R.id.menu_nav_sights:

                        break;
                    case R.id.menu_nav_routes:

                        break;
                    case R.id.menu_nav_help:

                        break;
                    case R.id.menu_nav_termsofservice:

                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //Code to show DetailedRouteFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailedRouteFragment detailedRouteFragment = new DetailedRouteFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainactivity_detailed_route_placeholder, detailedRouteFragment).addToBackStack(null).commit();

        mMapView = findViewById(R.id.mainactivity_map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        polylineOptions = new PolylineOptions();

        Intent intent = new Intent(getApplicationContext(),BackgroundService.class);
        startService(intent);
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleMap.clear();
                } else {
                    System.out.println("geen locatie");
                    finish();
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO open sightlistfragment???
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnInfoWindowClickListener(this);

        markers = mainViewModel.getAllRouteWaypoints();
        waypoints = new ArrayList<>();
        waypoints.add(locationHandler.getCurrentLocation());
        if (markers != null) {
            for (WayPointModel model : markers) {
                if(!model.isAlreadySeen()) {
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), model.getDescription());
                    waypoints.add(model.getLocation());
                }
                else
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), model.getDescription());
            }
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);

            mainViewModel.getRoutePoints(waypoints, this);
        }

    }

    @Override
    public void onRoutePointReceived(LatLng latLng) {
        polylineOptions.add(latLng);
        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if(googleMap != null)
            googleMap.clear();
        waypoints = new ArrayList<>();
        waypoints.add(latLng);
        polylineOptions = new PolylineOptions();
        polylineOptions.add(latLng);
        Toast.makeText(this, "Route updated to " + latLng, Toast.LENGTH_SHORT).show();
//        Commented because of max requests!
        if (markers != null) {
            for (WayPointModel model : markers) {
                if(!model.isAlreadySeen()) {
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap,
                                    model.getLocation(),
                                    BitmapDescriptorFactory.HUE_RED,
                                    model.getName(),
                                    model.getDescription());
                    waypoints.add(model.getLocation());
                }
                else
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap,
                                    model.getLocation(),
                                    BitmapDescriptorFactory.HUE_GREEN,
                                    model.getName(),
                                    model.getDescription());
            }
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);
            mainViewModel.getRoutePoints(waypoints, this);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent detailedIntent = new Intent(this, DetailedActivity.class);
        for (WayPointModel model : markers) {
            if(model.getLocation().equals(marker.getPosition())) {
                detailedIntent.putExtra("MARKERNUMBER", markers.indexOf(model));
                break;
            }
        }
        startActivity(detailedIntent);
    }
}
