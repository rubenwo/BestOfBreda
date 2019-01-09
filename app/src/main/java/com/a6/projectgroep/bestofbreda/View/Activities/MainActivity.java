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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedPreviewFragment;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;
import com.a6.projectgroep.bestofbreda.View.Fragments.TermsOfServiceFragment;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private static final int GPS_REQUEST = 50;

    private MainViewModel viewModel;
    private DrawerLayout drawerLayout;
    private MapView mMapView;
    private GoogleMap googleMap;
    private CameraPosition cameraPosition;

    private PolylineOptions walkedRouteOptions;
    private Polyline routePolyline;

    private List<Marker> mapMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mapMarkers = new ArrayList<>();

        setPolylineOptions();
        setupGoogleMaps(savedInstanceState);
        //setupDetailedRouteFragment();
        setupToolbar();
        setupDrawerLayout();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(googleMap != null) {
            outState.putParcelable("PolyLine", walkedRouteOptions);
            outState.putParcelable("CamPos", googleMap.getCameraPosition());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        walkedRouteOptions = savedInstanceState.getParcelable("PolyLine");
        cameraPosition = savedInstanceState.getParcelable("CamPos");
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

    private void setupDrawerLayout() {
        drawerLayout = findViewById(R.id.mainactivity_drawer_layout);

        NavigationView navigationView = findViewById(R.id.mainactivity_nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            //TODO navigate to the activities
            switch (menuItem.getItemId()) {
                case R.id.menu_nav_sights:
                    Intent sightIntent = new Intent(this, SightListActivity.class);
                    startActivity(sightIntent);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailedRouteFragment detailedRouteFragment = new DetailedRouteFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainactivity_detailed_route_placeholder, detailedRouteFragment).commit();
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
        searchItem.collapseActionView();
        searchView.setOnSearchClickListener(view -> {
            searchItem.collapseActionView();
            searchView.clearFocus();
            searchView.setIconified(true);
            Intent intent = new Intent(this, SightListActivity.class);
            startActivity(intent);
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
        } else {
            //request again for permission of location....
            googleMap.setMyLocationEnabled(true);
            if (cameraPosition != null) {
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                LatLng latLng = GeoCoderService.getInstance(getApplication()).getLocationFromName("Breda Centrum");
                if(latLng != null)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }

        viewModel.getCurrentLocation().observe(this, location -> {
            System.out.println("De huidige locatie is..." + location.toString());
            if(walkedRouteOptions != null) {
                walkedRouteOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
                googleMap.addPolyline(walkedRouteOptions);
            }

//            googleMap.addPolyline(walkedRouteOptions);
        });

        viewModel.getWayPoints().observe(this, points -> {
            Iterator it = mapMarkers.iterator();
            while(it.hasNext()) {
                Marker marker = (Marker) it.next();
                marker.remove();
                it.remove();
            }
            drawMarkers(points);
        });
        
        viewModel.getRoutePoints().observe(this, latLngs -> {
            if(routePolyline != null) {
                routePolyline.remove();
            }

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(10);
            polylineOptions.color(Color.BLUE);

            polylineOptions.addAll(latLngs);

            routePolyline = googleMap.addPolyline(polylineOptions);
            routePolyline.getPoints();
        });

        viewModel.getNearbyWayPoint().observe(this, waypointModel -> {
            if(waypointModel != null) {
                if(!waypointModel.isAlreadySeen()) {
                    waypointModel.setAlreadySeen(true);
                    DetailedPreviewFragment dFrag = DetailedPreviewFragment.newInstance(waypointModel.getName());
                    dFrag.show(getSupportFragmentManager(), "");
                }
            }
        });
    }

    private void drawMarkers(List<WaypointModel> markerPoints) {
        if (markerPoints != null) {

            List<WaypointModel> tempPoints = new ArrayList<>();
            boolean routeAvailable = true;
            RouteModel route = GoogleMapsAPIManager.getInstance(getApplication()).getSelectedRoute().getValue();
            if(route == null)
                routeAvailable = false;

            if(routeAvailable)
                for (String s : route.getRoute()) {
                    for (WaypointModel point : markerPoints) {
                        if(point.getName().equals(s)){
                            tempPoints.add(point);
                            break;
                        }
                    }
                }
            else
                tempPoints = markerPoints;



            for (int i = 0; i < tempPoints.size(); i++) {
                WaypointModel model = tempPoints.get(i);
                if(i == 0 && routeAvailable)
                    mapMarkers.add(GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_BLUE, model.getName(), Locale.getDefault().getLanguage().equals("nl") ? model.getDescriptionNL() : model.getDescriptionEN()));
                else if (!model.isAlreadySeen()) {
                    mapMarkers.add(GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), Locale.getDefault().getLanguage().equals("nl") ? model.getDescriptionNL() : model.getDescriptionEN()));
                } else
                    mapMarkers.add(GeoCoderService.getInstance(getApplication())
                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), Locale.getDefault().getLanguage().equals("nl") ? model.getDescriptionNL() : model.getDescriptionEN()));

            }
//            for (WaypointModel model : markerPoints) {
//                if (!model.isAlreadySeen()) {
//                    GeoCoderService.getInstance(getApplication())
//                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_RED, model.getName(), Locale.getDefault().getLanguage().equals("nl") ? model.getDescriptionNL() : model.getDescriptionEN());
//                } else
//                    GeoCoderService.getInstance(getApplication())
//                            .placeMarker(googleMap, model.getLocation(), BitmapDescriptorFactory.HUE_GREEN, model.getName(), Locale.getDefault().getLanguage().equals("nl") ? model.getDescriptionNL() : model.getDescriptionEN());
//            }
        }
    }

    private void setPolylineOptions() {
        walkedRouteOptions = new PolylineOptions();
        walkedRouteOptions.width(10);
        walkedRouteOptions.color(Color.GREEN);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("SightName", marker.getTitle());
        startActivity(intent);
    }
}