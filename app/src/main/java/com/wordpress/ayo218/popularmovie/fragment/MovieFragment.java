package com.wordpress.ayo218.popularmovie.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.activity.DetailActivity;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.utils.JsonHelperUtils;
import com.wordpress.ayo218.popularmovie.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    private static final String TAG = "MovieFragment";

    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<Movie> movieList = new ArrayList<>();
    ImageView emtptyView;


    // TODO: 5/17/2018 Change layout for movie item
    // TODO: 5/17/2018 Add fragment transition
    // TODO: 5/17/2018 change the url to sortby and discover...stuff like that
    // TODO: 5/17/2018 Give user the option to change settings(also preference or bundle to save app state)
    // TODO: 5/17/2018 Don't forget to use ButterView
    // TODO: 5/20/2018 Figure how to do infinite scroll
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
        emtptyView = view.findViewById(R.id.img_empty_view);
        recyclerView = view.findViewById(R.id.recyclerview_movie);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(getContext(), movieList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, movieList.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        loadMovies();
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
    private void loadMovies() {
        if (isConnected()) {
            new MovieAsyncTask().execute(Constants.TEST_API);
        } else{
            showEmptyView();
        }
    }

    private void showEmptyView() {
        emtptyView.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Please connect to the Internet", Toast.LENGTH_SHORT).show();
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
                movieList = movies;
                adapter.setMovieList(movieList);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "NO DATA", Toast.LENGTH_LONG).show();
            }
        }
    }
}

























