package com.wordpress.ayo218.popularmovie.database.favorites;

import android.content.Context;
import android.util.Log;

import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

public class FavoriteService {
    private Context context;
    private static final String TAG = "FavoriteService";

    public boolean isFavorite(Movie movie, int position){
       boolean favorite = false;

       //get the favorite movie ids from db
        List<Movie> favoriteMovies = FavoriteDatabase.getsInstance(context).favoriteDao().loadFavorite();
        long moviePosId = favoriteMovies.get(position).getMovie_id();
        Log.i(TAG, "isFavorite: " + favoriteMovies);
//
//        for (int i = 0; i < favoriteMovies.size(); i++) {
//            if (movie.getMovie_id() == favoriteMovies.get(i).getMovie_id()){
//                return true;
//            }
//        }
        return favorite;
    }
}
