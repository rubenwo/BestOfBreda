package com.a6.projectgroep.bestofbreda.View.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.DetailedViewModel;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {

    private final String TAG = "DETAILEDACTIVITY";
    private ViewPager viewPager;
    private DetailedViewModel viewModel;
    private WaypointModel model;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private String sightName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        titleTextView = findViewById(R.id.detailedactivity_sight_name);
        descriptionTextView = findViewById(R.id.detailedactivity_sight_description);
        viewPager = findViewById(R.id.detailedactivity_viewpager);
        ImageSliderAdapter adapter = new ImageSliderAdapter();

        viewPager.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(DetailedViewModel.class);
        viewModel.getAllWaypointModels().observe(this, new Observer<List<WaypointModel>>() {
            @Override
            public void onChanged(@Nullable List<WaypointModel> wayPointModels) {
                Log.i(TAG, "list changed");
            }
        });
        sightName = getIntent().getStringExtra("SightName");
        viewModel.getAllWaypointModels().observe(this, (List<WaypointModel> waypointModels) -> {
            for (WaypointModel m : waypointModels)
                if (sightName.equals(m.getName()))
                    model = m;
            titleTextView.setText(model.getName());
            if (Locale.getDefault().getLanguage().equals("nl"))
                descriptionTextView.setText(model.getDescriptionNL());
            else descriptionTextView.setText(model.getDescriptionEN());

            adapter.pictureUrls = model.getMultiMediaModel().getPictureUrls();
            adapter.notifyDataSetChanged();
        });
        CirclePageIndicator indicator = findViewById(R.id.detailedactivity_circlepageindicator);
        indicator.setViewPager(viewPager);
        indicator.setRadius(getResources().getDisplayMetrics().density * 5);

        FloatingActionButton playMedia = findViewById(R.id.detailedactivity_fab_media);
        playMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO get MultimediaModel
                MultimediaModel media = model.getMultiMediaModel();
                if(media.getVideoUrls() != null && !media.getVideoUrls().equals("")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(media.getVideoUrls()));
                    startActivity(browserIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.detailedactivity_toast_no_videos, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ImageSliderAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private List<String> pictureUrls;

        public ImageSliderAdapter() {
            inflater = LayoutInflater.from(getApplicationContext());
            pictureUrls = new ArrayList<>();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.detailedactivity_viewpager_item, container, false);
            ImageView imageView = imageLayout.findViewById(R.id.detailedactivity_viewpager_item_imageview);
            String url = pictureUrls.get(position);
            if(url.equals(""))
                return null;
            else if(url.startsWith("api"))
                url = "https://" + url;
            Picasso picasso = Picasso.get();
            picasso.setLoggingEnabled(true);
            picasso.load(url).into(imageView);
            container.addView(imageLayout);
            return imageLayout;
        }

        @Override
        public int getCount() {
            return pictureUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }
}
