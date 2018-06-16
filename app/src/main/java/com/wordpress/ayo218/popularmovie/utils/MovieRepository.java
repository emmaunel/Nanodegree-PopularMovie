package com.wordpress.ayo218.popularmovie.utils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.wordpress.ayo218.popularmovie.database.AppDatabase;
import com.wordpress.ayo218.popularmovie.database.FavoriteDao;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

public class MovieRepository {

    private FavoriteDao favoriteDao;
    private LiveData<List<Movie>> movieList;

    public MovieRepository(Application application) {
        AppDatabase database = AppDatabase.getsInstance(application);
        favoriteDao = database.favoriteDao();
        movieList = favoriteDao.loadAllMovie();
    }
    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public void insertMovie(Movie movie){
        new insertMovieAsyncTask(favoriteDao).execute(movie);
    }

    private static class insertMovieAsyncTask extends AsyncTask<Movie, Void, Void>{

        private FavoriteDao favoriteDao;

        insertMovieAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            favoriteDao.insertMovie(movies[0]);
            return null;
        }
    }
}
