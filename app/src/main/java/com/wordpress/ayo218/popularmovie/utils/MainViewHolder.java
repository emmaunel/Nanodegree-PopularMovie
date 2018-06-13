package com.wordpress.ayo218.popularmovie.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.wordpress.ayo218.popularmovie.database.AppDatabase;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

public class MainViewHolder extends AndroidViewModel {

    private LiveData<List<Movie>> moviesList;

    public MainViewHolder(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        moviesList = database.favoriteDao().loadAllMovie();

    }

    public LiveData<List<Movie>> getMoviesList() {
        return moviesList;
    }
}
