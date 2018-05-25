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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.scrollView) NestedScrollView scrollView;
    @BindView(R.id.movie_poster) ImageView imageView;
    private Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 5/20/2018 Change icon when clicked
                Snackbar.make(findViewById(R.id.coordinator_layout), "Added to Favorites", Snackbar.LENGTH_LONG).show();
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
