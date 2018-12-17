package com.a6.projectgroep.bestofbreda.View.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.DetailedViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private final String TAG = "DETAILEDACTIVITY";
    private ViewPager viewPager;
    private DetailedViewModel viewModel;
    private int sightID;
    private WaypointModel model;
    private TextView titleTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        titleTextView = findViewById(R.id.detailedactivity_sight_name);
        descriptionTextView = findViewById(R.id.detailedactivity_sight_description);
        viewPager = findViewById(R.id.detailedactivity_viewpager);

        viewPager.setAdapter(new ImageSliderAdapter());

        viewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);
        viewModel.getAllWaypointModels().observe(this, new Observer<List<WaypointModel>>() {
            @Override
            public void onChanged(@Nullable List<WaypointModel> wayPointModels) {
                Log.i(TAG, "list changed");
            }
        });
        sightID = getIntent().getIntExtra("ROUTE_ADAPTERPOS", 0);
        LatLng position = getIntent().getParcelableExtra("POSITION");
        viewModel.getAllWaypointModels().observe(this, (List<WaypointModel> waypointModels) -> {
            for (WaypointModel m : waypointModels)
                if (position.equals(m.getLocation()))
                    model = m;
            titleTextView.setText(model.getName());
            descriptionTextView.setText(model.getDescription());
        });

        CirclePageIndicator indicator = findViewById(R.id.detailedactivity_circlepageindicator);
        indicator.setViewPager(viewPager);
        indicator.setRadius(getResources().getDisplayMetrics().density * 5);

        FloatingActionButton playMedia = findViewById(R.id.detailedactivity_fab_media);
        playMedia.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO get MultimediaModel
//                if(media.getVideoUrls() != null){
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(media.getVideoUrls()));
//                    startActivity(browserIntent);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), R.string.detailedactivity_toast_no_videos, Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private class ImageSliderAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public ImageSliderAdapter() {
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.detailedactivity_viewpager_item, container, false);
            ImageView imageView = imageLayout.findViewById(R.id.detailedactivity_viewpager_item_imageview);
            String url = viewModel.getAllMultimediaModels().getValue().get(sightID).getPictureUrls().get(position);
            Picasso.get().load(url).into(imageView);
            container.addView(imageLayout);
            return imageLayout;
        }

        @Override
        public int getCount() {
            //return viewModel.getAllWaypointModels().getValue().get(sightID).getMultimediaID().getPictureUrls().size();
            //TODO: get multimediaID from waypoint and search for it in multimediaDAO.
            return -1;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }
}
