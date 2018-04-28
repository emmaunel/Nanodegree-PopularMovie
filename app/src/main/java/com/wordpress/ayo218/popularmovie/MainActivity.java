package com.wordpress.ayo218.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_fragment_movie)
    RecyclerView recyclerView;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fillGridView();
    }

    private void fillGridView() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Games", R.drawable.ic_launcher_background));
        movies.add(new Movie("ames", R.drawable.ic_launcher_background));
        movies.add(new Movie("mes", R.drawable.ic_launcher_background));
        movies.add(new Movie("Ges", R.drawable.ic_launcher_background));
        movies.add(new Movie("Gas", R.drawable.ic_launcher_background));
        movies.add(new Movie("Game", R.drawable.ic_launcher_background));
        movies.add(new Movie("Gamedds", R.drawable.ic_launcher_background));

        adapter = new MovieAdapter(this, movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}
