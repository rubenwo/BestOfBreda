package com.a6.projectgroep.bestofbreda.View.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.DetailedViewModel;

public class DetailedActivity extends AppCompatActivity {

    private TextView wayPointName;
    private WayPointModel model;
    private DetailedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        viewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);

        wayPointName = findViewById(R.id.detailedactivity_sight_name);
        wayPointName.setText(model.getName());
    }
}
