package com.wordpress.ayo218.popularmovie.fragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment{

    RecyclerView recyclerView;
    MovieAdapter adapter;

    public MovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));

        recyclerView = view.findViewById(R.id.recyclerview_movie);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(getContext(), movieList);
        recyclerView.setAdapter(adapter);
    }
}
