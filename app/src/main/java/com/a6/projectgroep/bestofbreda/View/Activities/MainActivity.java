package com.a6.projectgroep.bestofbreda.View.Activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
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
import com.a6.projectgroep.bestofbreda.Services.GeoCoderService;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;
import com.a6.projectgroep.bestofbreda.ViewModel.MainViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int GPS_REQUEST = 50;
    private MainViewModel mainViewModel;
    private DrawerLayout drawerLayout;
    private MapView mMapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
        mainViewModel = new MainViewModel(getApplication());

        mMapView = findViewById(R.id.mainactivity_map_view);
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
                    //DOE IETS
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
        List<WayPointModel> markers = mainViewModel.getAllWaypointModels().getValue();
        if(markers != null){
            for (WayPointModel model: markers) {
                GeoCoderService.getInstance(getApplication()).placeMarker(mMap, model.getLocation(), 222, model.getName(), model.getDescription());
            }
            mainViewModel.getRoute(markers.get(0).getLocation(), markers.get(1).getLocation());
        }
    }
}
