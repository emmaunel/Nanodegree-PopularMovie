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
            // TODO: 5/17/2018 Check the API for the right name
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");
            String vote_average = jsonObject.getString("vote_average");

            Movie movie = new Movie(title, img_path, overview, release_date, vote_average);

//            Movie movie1 = new Movie();
//            movie1.setMovie_title(title);
//            movie1.setPoster_path(img_path);
//            movie1.setOverview(overview);
//            movie1.setRelease_date(release_date);
//            movie1.setVote_average(vote_average);

            Log.e(TAG, "getDiscoverMovie: " + movie.getMovie_title());
            Log.e(TAG, "getDiscoverMovie: " + movie.getRelease_date());
            parsedMovie.add(movie);
        }

        return parsedMovie;
    }
}
