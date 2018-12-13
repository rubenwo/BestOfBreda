package com.a6.projectgroep.bestofbreda.View.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.a6.projectgroep.bestofbreda.R;

public class DetailedVideoFragment extends DialogFragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_video, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Uri uri = Uri.parse("://vimeo.com/285110080/");
        VideoView videoView = view.findViewById(R.id.detailedactivity_videoview);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
