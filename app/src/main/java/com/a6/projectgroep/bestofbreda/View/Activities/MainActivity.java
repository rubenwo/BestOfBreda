package com.a6.projectgroep.bestofbreda.View.Activities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.RouteReceivedListener;
import com.a6.projectgroep.bestofbreda.Services.database.NavigationDatabase;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;
import com.a6.projectgroep.bestofbreda.View.Fragments.TermsOfServiceFragment;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, RouteReceivedListener, GoogleMap.OnInfoWindowClickListener {
    private static final int GPS_REQUEST = 50;

    private MainViewModel viewModel;
    private DrawerLayout drawerLayout;
    private MapView mMapView;
    private GoogleMap googleMap;

    private PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);



        setupGoogleMaps(savedInstanceState);
        setupDetailedRouteFragment();
        setupToolbar();
        setupDrawerLayout();

        //setupViewModel();

//        NavigationDatabase.getInstance(getApplication()).routeDAO().getLiveRoute("nameOfRoute").observe(this, routeModel -> {
//            viewModel.setRoute(routeModel);
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void setupGoogleMaps(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.mainactivity_map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_nav);
    }

    private void setupViewModel() {
        viewModel.getAllWaypointModels().observe(this, wayPointModels -> {
            Toast.makeText(getApplicationContext(), "waypoints changed", Toast.LENGTH_SHORT).show();
            for (WaypointModel m : wayPointModels) {
                Log.i("DATABASE_MODELS", m.toString());
            }
        });

        viewModel.getAllRouteModels().observe(this, routeModels -> {
            Toast.makeText(getApplicationContext(), "routes changed", Toast.LENGTH_SHORT).show();
            for (RouteModel m : routeModels) {
                Log.i("DATABASE_MODELS", m.toString());
                viewModel.createWaypointModelList();
            }
        });

        viewModel.getAllMultiMediaModels().observe(this, multimediaModels -> {
            Toast.makeText(getApplicationContext(), "multimedia changed", Toast.LENGTH_SHORT).show();
            for (MultimediaModel m : multimediaModels) {
                Log.i("DATABASE_MODELS", m.toString());
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
                    Intent intent = new Intent(this, RouteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_nav_help:
                    Intent helpIntent = new Intent(this, HelpActivity.class);
                    startActivity(helpIntent);
                    break;
                case R.id.menu_nav_termsofservice:
                    DialogFragment fragment = new TermsOfServiceFragment();
                    fragment.show(getSupportFragmentManager(), "TOS");
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
        fragmentTransaction.add(R.id.mainactivity_detailed_route_placeholder, detailedRouteFragment).commit();

//        mMapView = findViewById(R.id.mainactivity_map_view);
//        //mMapView.onCreate(savedInstanceState);
//        mMapView.onResume();
//        try {
//            MapsInitializer.initialize(getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mMapView.getMapAsync(this);
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
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    assert i != null;
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "geen locatie", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(view -> {
            //TODO open sightlistfragment???
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
        googleMap.setOnInfoWindowClickListener(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else {
            //request again for permission of location....
            googleMap.setMyLocationEnabled(true);
        }

        viewModel.getCurrentLocation().observe(this, location -> {
            System.out.println("De huidige locatie is..." + location.toString());
        });

        viewModel.getWayPoints().observe(this, points -> {
            drawMarkers(points);
        });

//        NavigationDatabase.getInstance(getApplication()).waypointDAO().getAllWayPoints().observe(this, waypointModels1 -> {
//            drawMarkers(waypointModels1);
//        });
        //drawMarkers();
        //drawRoute();
        //onLocationChanged(viewModel.getCurrentPosition());
    }

    private void drawMarkers(List<WaypointModel> markerPoints) {
//        markers = waypointModels.getValue();
//        waypoints = new ArrayList<>();
//        waypoints.add(viewModel.getCurrentPosition());
        if (markerPoints != null) {
            for (WaypointModel model : markerPoints) {
                if (!model.isAlreadySeen()) {
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), model.getDescription());
                    //waypoints.add(model.getLocation());
                } else
                    GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), model.getDescription());
            }
        }
    }

    private void drawRoute() {
        PolylineOptions walkedRouteOptions;
        polylineOptions = new PolylineOptions();
//        polylineOptions.add(latLng);
//        walkedRouteOptions.add(latLng);
        polylineOptions.width(10);
        polylineOptions.color(Color.BLUE);
        polylineOptions.add(viewModel.getCurrentPosition());
    }

    @Override
    public void onRoutePointReceived(LatLng latLng) {
        polylineOptions.add(latLng);
        googleMap.addPolyline(polylineOptions);
    }

//    @Override
//    public void onLocationChanged(LatLng latLng) {
//        if(latLng == null)
//            return;
//        if (googleMap != null) {
//            googleMap.clear();
//        }
//        waypoints = new ArrayList<>();
//        waypoints.add(latLng);
//
//
////        if (googleMap != null) {
////            googleMap.clear();
////            googleMap.addPolyline(walkedRouteOptions);
////        }
//
//
//        //Toast.makeText(this, "Route updated to " + latLng, Toast.LENGTH_SHORT).show();
//
//        if (markers != null) {
//            for (WaypointModel model : markers) {
//                if (!model.isAlreadySeen()) {
//                    GeoCoderService.getInstance(getApplication())
//                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), model.getDescription());
//                    waypoints.add(model.getLocation());
//                } else
//                    GeoCoderService.getInstance(getApplication())
//                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), model.getDescription());
//            }
//
//            viewModel.getRoutePoints(waypoints, this);
//        }
//    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("POSITION", marker.getPosition());
        startActivity(intent);
    }
}