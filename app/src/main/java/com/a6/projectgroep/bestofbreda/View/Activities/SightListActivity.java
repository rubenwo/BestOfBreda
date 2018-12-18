package com.a6.projectgroep.bestofbreda.View.Activities;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.Adapters.SightsRecyclerviewAdapter;
import com.a6.projectgroep.bestofbreda.ViewModel.SightsListViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SightListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SightsRecyclerviewAdapter adapter;
    private SightsListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sight_list);

        viewModel = ViewModelProviders.of(this).get(SightsListViewModel.class);

        Toolbar toolbar = findViewById(R.id.sightlistfragment_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.sightlistfragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new SightsRecyclerviewAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        viewModel.getAllWaypoinModels().observe(this, waypointModels -> {
            assert waypointModels != null;
            if(!waypointModels.isEmpty())
                adapter.setSightList(waypointModels);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.setDataset(s);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setDataset(s);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
