package com.wordpress.ayo218.popularmovie.model;

public class Movie {
    private String movie_title;
    private String poster_path;

    public Movie(String movie_title, String poster_path) {
        this.movie_title = movie_title;
        this.poster_path = poster_path;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
