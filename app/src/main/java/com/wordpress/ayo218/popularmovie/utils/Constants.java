package com.wordpress.ayo218.popularmovie.utils;

import com.wordpress.ayo218.popularmovie.BuildConfig;

public class Constants{
    public static final String API_KEY = BuildConfig.API_KEY;

    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w780/";

    public static final String POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
    public static final String TOP_RATED_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";


    public static final String API_APPEND = "api_key";
    public static final String PAGE_APPEND = "page";
    public static final String REVIEW_PATH = "reviews";
    public static final String TRAILER_PATH = "videos";

}
