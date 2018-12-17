package com.a6.projectgroep.bestofbreda.View.Activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.BackgroundService;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.LiveLocationListener;
import com.a6.projectgroep.bestofbreda.Services.RouteReceivedListener;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedPreviewFragment;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.a6.projectgroep.bestofbreda.ViewModel.ViewModelFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, RouteReceivedListener, LiveLocationListener {
    private static final int GPS_REQUEST = 50;
    private MainViewModel mainViewModel;
    private DrawerLayout drawerLayout;
    private MapView mMapView;
    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private List<WaypointModel> markers;
    private ArrayList<LatLng> waypoints;
    private Bundle savedInstanceState;
    private DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        askPermission();
        setupDetailedRouteFragment();
        setupToolbar();
        setupDrawerLayout();
        setupViewModel();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_nav);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), this::onLocationChanged)).get(MainViewModel.class);
        mainViewModel.getAllWaypointModels().observe(this, wayPointModels -> {
            Toast.makeText(getApplicationContext(), "waypoints changed", Toast.LENGTH_SHORT).show();
            for (WaypointModel m : wayPointModels) {
                Log.i("DATABASE_MODELS", m.toString());
            }
        });

        mainViewModel.getAllRouteModels().observe(this, new Observer<List<RouteModel>>() {
            @Override
            public void onChanged(@Nullable List<RouteModel> routeModels) {
                Toast.makeText(getApplicationContext(), "routes changed", Toast.LENGTH_SHORT).show();
                for (RouteModel m : routeModels) {
                    Log.i("DATABASE_MODELS", m.toString());
                }
            }
        });

        mainViewModel.getAllMultiMediaModels().observe(this, new Observer<List<MultimediaModel>>() {
            @Override
            public void onChanged(@Nullable List<MultimediaModel> multimediaModels) {
                Toast.makeText(getApplicationContext(), "multimedia changed", Toast.LENGTH_SHORT).show();
                for (MultimediaModel m : multimediaModels) {
                    Log.i("DATABASE_MODELS", m.toString());
                }
            }
        });
    }

    private void setupDrawerLayout() {
        drawerLayout = findViewById(R.id.mainactivity_drawer_layout);

        NavigationView navigationView = findViewById(R.id.mainactivity_nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            //TODO navigate to the activities
            switch (menuItem.getItemId()) {
                case R.id.menu_nav_sights:
                    break;
                case R.id.menu_nav_routes:

                    break;
                case R.id.menu_nav_help:
                    Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_nav_termsofservice:

                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });

    }

        private void setupDetailedRouteFragment() {
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
        markers = mainViewModel.getAllRouteWaypoints();
        waypoints = new ArrayList<>();
        waypoints.add(mainViewModel.getCurrentPosition());

        if (markers != null) {
            for (WaypointModel model : markers) {
                if (!model.isAlreadySeen()) {
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), model.getDescription());
                    waypoints.add(model.getLocation());
                } else
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), model.getDescription());
            }
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);
            polylineOptions.add(mainViewModel.getCurrentPosition());

            mainViewModel.getRoutePoints(waypoints, this);
            onLocationChanged(mainViewModel.getCurrentPosition());
        }

    }

    @Override
    public void onRoutePointReceived(LatLng latLng) {
        polylineOptions.add(latLng);
        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        if (googleMap != null)
            googleMap.clear();
        waypoints = new ArrayList<>();
        waypoints.add(latLng);
        polylineOptions = new PolylineOptions();
        polylineOptions.add(latLng);
//        Toast.makeText(this, "Route updated to " + latLng, Toast.LENGTH_SHORT).show();
//        Commented because of max requests!
        if (markers != null) {
            for (WaypointModel model : markers) {
                if (!model.isAlreadySeen()) {
                    Location modelLocation = new Location("modelLocation");
                    modelLocation.setLatitude(model.getLocation().latitude);
                    modelLocation.setLongitude(model.getLocation().longitude);
                    Location currentLocation = new Location("peoplLocation");
                    currentLocation.setLatitude(mainViewModel.getCurrentPosition().latitude);
                    currentLocation.setLongitude(mainViewModel.getCurrentPosition().longitude);
                    if(currentLocation.distanceTo(modelLocation) < 35) {
                        if(dialogFragment != null) {
                            if(!dialogFragment.isVisible()) {
                                dialogFragment = DetailedPreviewFragment.newInstance(model.getName());
                                dialogFragment.show(getSupportFragmentManager(), null);
                            }
                        }
                        else {
                            dialogFragment = DetailedPreviewFragment.newInstance(model.getName());
                            dialogFragment.show(getSupportFragmentManager(), null);
                        }
                        model.setAlreadySeen(true);
                    }

                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), model.getDescription());
                    waypoints.add(model.getLocation());
                } else
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), model.getDescription());

            }
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);
            mainViewModel.getRoutePoints(waypoints, this);
        }
    }
}
