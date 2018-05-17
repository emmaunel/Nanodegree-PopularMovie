package com.wordpress.ayo218.popularmovie.fragment;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.utils.JsonHelperUtils;
import com.wordpress.ayo218.popularmovie.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment{
    private static final String TAG = "MovieFragment";

    RecyclerView recyclerView;
    MovieAdapter adapter;


    // TODO: 5/17/2018 Add empty view for network failure
    // TODO: 5/17/2018 Check for network connectivity
    // TODO: 5/17/2018 Change layour for movie item
    // TODO: 5/17/2018 Add fragment transition
    // TODO: 5/17/2018 change the url to sortby and discover...stuff like that
    // TODO: 5/17/2018 Give user the option to change settings(also preference or bundle to save app state)
    // TODO: 5/17/2018 Don't forget to use ButterView
    public MovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<Movie> movieList = new ArrayList<>();
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Games", R.drawable.ic_launcher_background));

        recyclerView = view.findViewById(R.id.recyclerview_movie);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(getContext(), movieList);
        recyclerView.setAdapter(adapter);

        loadMovies();
    }

    private void loadMovies() {
        new MovieAsyncTask().execute(Constants.TEST_API);
    }

    private class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected List<Movie> doInBackground(String... strings) {
            String movies = strings[0];
            URL movieUrl = NetworkUtils.buildUrl(movies);

            try {
                String jsonResponce = NetworkUtils.getResponseFromHttpUrl(movieUrl);
                Log.i(TAG, "doInBackground: It worked");

                List<Movie> json = JsonHelperUtils.getDiscoverMovie(getContext(), jsonResponce);
                Log.i(TAG, "doInBackground: Successful");

                return json;
            } catch (Exception e){
                e.printStackTrace();
                Log.e(TAG, "doInBackground: " + e.getMessage());
                return null;
            }
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                adapter.setMovieList(movies);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "NO DATA", Toast.LENGTH_LONG).show();
            }
        }
    }
}

























