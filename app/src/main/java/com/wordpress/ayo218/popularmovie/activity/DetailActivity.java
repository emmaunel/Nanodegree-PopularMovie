package com.wordpress.ayo218.popularmovie.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.fragment.MovieDetailFragment;
import com.wordpress.ayo218.popularmovie.model.Movie;

public class DetailActivity extends AppCompatActivity {
    private Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.movie_poster);
        FloatingActionButton fab = findViewById(R.id.fab);
        NestedScrollView scrollView = findViewById(R.id.scrollView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 5/20/2018 Change icon when clicked
                // TODO: 5/23/2018 FIx this 
                Snackbar.make(getCurrentFocus(), "Added to Favorites", Snackbar.LENGTH_LONG).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        data = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        collapsingToolbarLayout.setTitle(data.getMovie_title());
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(data.getBackdrop_path()))
                .centerCrop()
                .fit()
                .into(imageView);

        intiFragment();
    }

    private void intiFragment() {
        MovieDetailFragment fragment = new MovieDetailFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.movie_detail, fragment);
        transaction.commit();
    }

}
