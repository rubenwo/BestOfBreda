package com.a6.projectgroep.bestofbreda.View.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.Services.GoogleMapsAPIManager;
import com.a6.projectgroep.bestofbreda.ViewModel.Adapters.RoutesRecyclerviewAdapter;
import com.a6.projectgroep.bestofbreda.ViewModel.RouteListViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements RoutesRecyclerviewAdapter.OnSelectRouteListener {

    private RecyclerView recyclerView;
    private RoutesRecyclerviewAdapter adapter;
    private RouteListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        viewModel = ViewModelProviders.of(this).get(RouteListViewModel.class);

        Toolbar toolbar = findViewById(R.id.routeactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.routeactivity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        viewModel.getAllRouteModels().observe(this, routeModels -> {
            assert routeModels != null;
            if(!routeModels.isEmpty())
                adapter.setRoutes(routeModels);
            adapter.notifyDataSetChanged();
        });

        adapter = new RoutesRecyclerviewAdapter(getApplicationContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        //TODO filter recyclerview adapter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onSelectRoute(RouteModel routeModel) {
        GoogleMapsAPIManager.getInstance(getApplication()).setCurrentRoute(routeModel);
        super.onBackPressed();
    }
}
