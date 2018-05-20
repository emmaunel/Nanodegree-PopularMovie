package com.wordpress.ayo218.popularmovie.utils;

import android.content.Context;
import android.util.Log;

import com.wordpress.ayo218.popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHelperUtils {
    private static final String TAG = "JsonHelperUtils";

    public static List<Movie> getDiscoverMovie(Context context, String json) throws JSONException{
        List<Movie> parsedMovie = new ArrayList<>();

        JSONObject result = new JSONObject(json);
        JSONArray resultArray = result.getJSONArray("results");

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject jsonObject = resultArray.getJSONObject(i);

            String title = jsonObject.getString("title");
            String img_path = jsonObject.getString("poster_path");
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");
            String vote_average = jsonObject.getString("vote_average");

            Movie movie = new Movie(title, img_path, overview, release_date, vote_average);

            Log.e(TAG, "getDiscoverMovie: " + movie.getMovie_title());
            Log.e(TAG, "getDiscoverMovie: " + movie.getRelease_date());
            parsedMovie.add(movie);
        }

        return parsedMovie;
    }
}
