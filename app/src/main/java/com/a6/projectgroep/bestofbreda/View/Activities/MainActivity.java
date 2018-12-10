package com.a6.projectgroep.bestofbreda.View.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Fragments.DetailedRouteFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_nav);
        actionBar.setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.mainactivity_drawer_layout);

        NavigationView navigationView = findViewById(R.id.mainactivity_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                menuItem.setChecked(true);
                //TODO navigate to the activities
                switch (menuItem.getItemId()){
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_mainactivity_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO open sightlistfragment???
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
