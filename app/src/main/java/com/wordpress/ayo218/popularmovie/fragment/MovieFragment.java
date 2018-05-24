package com.wordpress.ayo218.popularmovie.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.SettingActivity;
import com.wordpress.ayo218.popularmovie.activity.DetailActivity;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment {
    private static final String TAG = "MovieFragment";

    @BindView(R.id.recyclerview_movie) RecyclerView recyclerView;
    @BindView(R.id.img_empty_view) ImageView emptyView;

    MovieAdapter adapter;
    List<Movie> movieList = new ArrayList<>();
    String sortby_options;

    // TODO: 5/20/2018 Figure how to do infinite scroll

    public MovieFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        setHasOptionsMenu(true);
//        listener = new EndlessRecyclerViewOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                // TODO: 5/20/2018 Load More function
//                //loadMore();
//            }
//        };
//
//        recyclerView.addOnScrollListener(listener);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isConnected()) {
            loadMovies();
        } else {
            showEmptyView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!movieList.isEmpty() && isConnected()) {
            movieList.clear();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private void loadMovies() {
        Uri uri;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sortby_options = preferences.getString(getString(R.string.sort), getString(R.string.sort_popular));

        if (sortby_options.equals(getString(R.string.sort_popular))) {
            uri = Uri.parse(Constants.POPULAR_BASE_URL).buildUpon()
                    .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                    .build();

        } else {
            uri = Uri.parse(Constants.TOP_RATED_BASE_URL).buildUpon()
                    .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                    .build();
        }

        String uri_string = uri.toString();

        //Network functionality
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri_string, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < 20; i++) {
                                JSONObject result = response.getJSONArray("results").getJSONObject(i);
                                String movie_title = result.getString("title");
                                String img_path = result.getString("poster_path");
                                String back_drop_path = result.getString("backdrop_path");
                                String overview = result.getString("overview");
                                String release_date = result.getString("release_date");
                                String vote_average = result.getString("vote_average");

                                movieList.add(new Movie(movie_title, img_path, back_drop_path, overview, release_date, vote_average));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    private void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Please connect to the Internet", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                startActivity(new Intent(getContext(), SettingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

























