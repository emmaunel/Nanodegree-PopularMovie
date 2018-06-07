package com.wordpress.ayo218.popularmovie.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> loadAllMovies();

    @Insert
    void insertMovie(Movie movie);

    //maybe update
    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
