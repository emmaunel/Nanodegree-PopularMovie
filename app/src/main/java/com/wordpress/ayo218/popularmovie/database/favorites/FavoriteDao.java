package com.wordpress.ayo218.popularmovie.database.favorites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM movie")
    List<Movie> loadFavorite();

    @Insert
    void insertFavorite(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE movie_id = :id")
    Movie getMovieById(long id);

}
