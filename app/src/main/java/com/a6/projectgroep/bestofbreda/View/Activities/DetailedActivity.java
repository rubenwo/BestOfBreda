package com.a6.projectgroep.bestofbreda.View.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a6.projectgroep.bestofbreda.R;
import com.viewpagerindicator.CirclePageIndicator;

public class DetailedActivity extends AppCompatActivity {

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        viewPager = findViewById(R.id.detailedactivity_viewpager);
        viewPager.setAdapter(new ImageSliderAdapter());
        CirclePageIndicator indicator = findViewById(R.id.detailedactivity_circlepageindicator);
        indicator.setViewPager(viewPager);
        indicator.setRadius(getResources().getDisplayMetrics().density * 5);

    }

    private class ImageSliderAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        public ImageSliderAdapter() {
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position)
        {
            ViewGroup imageLayout= (ViewGroup) inflater.inflate(R.layout.detailedactivity_viewpager_item, container, false);
            ImageView imageView = imageLayout.findViewById(R.id.detailedactivity_viewpager_item_imageview);
            //TODO load image with picasso
            container.addView(imageLayout);
            return imageLayout;
        }

        @Override
        public int getCount()
        {
            //TODO return size of viewmodel livedata list
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
        {
            return view == o;
        }
    }
}
