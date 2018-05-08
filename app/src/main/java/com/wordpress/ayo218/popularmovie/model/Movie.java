package com.wordpress.ayo218.popularmovie.model;

public class Movie {
    private String movie_title;
    private int movie_image;

    public Movie(String movie_title, int movie_image) {
        this.movie_title = movie_title;
        this.movie_image = movie_image;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public int getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(int movie_image) {
        this.movie_image = movie_image;
    }
}
