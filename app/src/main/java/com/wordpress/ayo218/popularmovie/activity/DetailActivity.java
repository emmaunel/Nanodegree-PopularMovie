package com.wordpress.ayo218.popularmovie.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_poster)
    ImageView imageView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

//        initToolbar();
//        getIncomingIntent();
    }

    private void getIncomingIntent() {
//        Intent getIntent = getIntent();
////        title = getIntent.getStringExtra(MovieAdapter.MOVIE_TITLE_EXTRA);
////        Log.e(TAG, "getIncomingIntent: "+ title);

        Movie movie = (Movie)getIntent().getParcelableExtra(MovieAdapter.MOVIE_OBJECT);
        Log.e(TAG, "getIncomingIntent: " + movie);
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        Picasso.with(this)
                .load(R.drawable.ic_launcher_foreground)
                .fit()
                .into(imageView);
    }
}
