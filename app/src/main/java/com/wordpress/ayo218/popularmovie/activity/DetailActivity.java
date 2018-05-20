package com.wordpress.ayo218.popularmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView imageView = findViewById(R.id.movie_poster);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NestedScrollView scrollView = findViewById(R.id.scrollView);

        Movie movieData = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        toolbarLayout.setTitle(movieData.getMovie_title());

        Picasso.get()
                .load(R.drawable.ic_launcher_background)
                .fit()
                .into(imageView);
    }


    private void initToolbar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setActionBar(toolbar);
//        }
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
    }
}
