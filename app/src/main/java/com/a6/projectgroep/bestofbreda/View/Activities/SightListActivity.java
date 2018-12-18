package com.a6.projectgroep.bestofbreda.View.Activities;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.Adapters.SightsRecyclerviewAdapter;
import com.a6.projectgroep.bestofbreda.ViewModel.RouteListViewModel;
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


        recyclerView = findViewById(R.id.sightlistfragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        adapter = new SightsRecyclerviewAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        viewModel.getAllWaypoinModels().observe(this, waypointModels -> {
            assert waypointModels != null;
            if(!waypointModels.isEmpty())
                adapter.setSightList(waypointModels);
            adapter.notifyDataSetChanged();
        });
    }
}
