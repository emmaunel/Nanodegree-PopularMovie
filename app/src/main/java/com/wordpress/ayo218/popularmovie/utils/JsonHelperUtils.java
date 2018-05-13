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

            Movie movie = new Movie(title, img_path);
            Log.e(TAG, "getDiscoverMovie: " + movie.getMovie_title());
            parsedMovie.add(movie);
        }

        return parsedMovie;
    }
}
