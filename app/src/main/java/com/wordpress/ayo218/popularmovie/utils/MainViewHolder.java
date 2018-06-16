package com.wordpress.ayo218.popularmovie.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

public class MainViewHolder extends AndroidViewModel {

    private LiveData<List<Movie>> moviesList;
    private MovieRepository repository;

    public MainViewHolder(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        moviesList = repository.getMovieList();

    }

    public LiveData<List<Movie>> getMoviesList() {
        return moviesList;
    }

    public void insertMovie(Movie movie){
        repository.insertMovie(movie);
    }
}
