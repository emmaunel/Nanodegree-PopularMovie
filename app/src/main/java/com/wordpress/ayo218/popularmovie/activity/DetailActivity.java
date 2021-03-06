package com.wordpress.ayo218.popularmovie.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.database.AppDatabase;
import com.wordpress.ayo218.popularmovie.database.AppExecutors;
import com.wordpress.ayo218.popularmovie.fragment.MovieDetailFragment;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.utils.Constants;
import com.wordpress.ayo218.popularmovie.utils.MainViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    @BindView(R.id.collapsing_toolbar_layout)
     CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
     Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.movie_poster)
     ImageView imageView;

    Movie data;
    Boolean favorite = false;

    private AppDatabase database;
    private MainViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        database = AppDatabase.getsInstance(getApplicationContext());
        holder = ViewModelProviders.of(this).get(MainViewHolder.class);
        data = getIntent().getParcelableExtra(Intent.EXTRA_TEXT); 
        fab.setOnClickListener(this::favoriteUpdate);

        initViews();
        initFragment();
        checkFavorites();
    }

    private void initViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle(data.getMovie_title());
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(data.getBackdrop_path()))
                .centerCrop()
                .fit()
                .into(imageView);
    }

    private void initFragment() {
        MovieDetailFragment fragment = new MovieDetailFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.movie_detail, fragment);
        transaction.commit();
    }

    private void favoriteUpdate(View view){
        if (favorite){
            favorite = false;
            changeFabImage();
            removeMovie();
            Snackbar.make(view, "Removed From Favorites", Snackbar.LENGTH_LONG).show();
        } else{
            favorite = true;
            changeFabImage();
            saveMovie();
            Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_LONG).show();

        }
    }

    private void changeFabImage(){
        if (favorite){
            fab.setImageResource(R.drawable.ic_favorite_white);
        } else {
            fab.setImageResource(R.drawable.ic_favorite);
        }
    }

    private void removeMovie(){
        int movie_id = data.getMovie_id();
        String movie_title = data.getMovie_title();
        String poster_path = data.getPoster_path();
        String backdrop_path = data.getBackdrop_path();
        String overview = data.getOverview();
        String release_date = data.getRelease_date();
        String vote_average = data.getVote_average();

        Movie movie = new Movie(movie_id,movie_title,poster_path,backdrop_path,
                overview,release_date,vote_average);

        AppExecutors.getsInstance().diskIO().execute( () -> database.favoriteDao().deleteMovie(movie));
    }

    private void saveMovie(){
        int movie_id = data.getMovie_id();
        String movie_title = data.getMovie_title();
        String poster_path = data.getPoster_path();
        String backdrop_path = data.getBackdrop_path();
        String overview = data.getOverview();
        String release_date = data.getRelease_date();
        String vote_averagae = data.getVote_average();

        Movie movie = new Movie(movie_id,movie_title,poster_path,backdrop_path,
                overview,release_date,vote_averagae);
        holder.insertMovie(movie);
    }

    private void checkFavorites(){
        AppExecutors.getsInstance().diskIO().execute(() -> {
            Movie movie = database.favoriteDao().getMovieById(data.getMovie_id());
            favorite = movie != null;
            runOnUiThread(this::changeFabImage);
        });
    }

}
