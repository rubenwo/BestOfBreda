package com.a6.projectgroep.bestofbreda.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Activities.DetailedActivity;

public class DetailedPreviewFragment extends DialogFragment
{
    public static DetailedPreviewFragment newInstance(String sightTitle)
    {
        DetailedPreviewFragment fragment = new DetailedPreviewFragment();
        Bundle args = new Bundle();
        args.putString("SIGHTNAME", sightTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_preview, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        TextView sightTitle = view.findViewById(R.id.detailedfragment_textview_sight_name);
        sightTitle.setText(getArguments().getString("SIGHTNAME"));

        Button moreInfoButton = view.findViewById(R.id.detailedfragment_button);
        moreInfoButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), DetailedActivity.class);
            //ToDo add waypoint id or waypoint object
            startActivity(intent);
        });
    }
}
