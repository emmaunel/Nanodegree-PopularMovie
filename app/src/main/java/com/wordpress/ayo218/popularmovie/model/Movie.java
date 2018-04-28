package com.wordpress.ayo218.popularmovie.model;

/**
 * Created by Ayo on 4/28/2018.
 */

public class Movie {
    private String MovieTitle;
    private int MovieImage;

    public Movie() {
    }

    public Movie(String movieTitle, int movieImage) {
        MovieTitle = movieTitle;
        MovieImage = movieImage;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }

    public int getMovieImage() {
        return MovieImage;
    }

    public void setMovieImage(int movieImage) {
        MovieImage = movieImage;
    }
}
