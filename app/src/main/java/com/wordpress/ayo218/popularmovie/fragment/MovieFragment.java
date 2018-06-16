package com.wordpress.ayo218.popularmovie.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.activity.DetailActivity;
import com.wordpress.ayo218.popularmovie.activity.SettingActivity;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment {
    private static final String TAG = "MovieFragment";
    private static final String RECYCLER_POSITION = "position";

    @BindView(R.id.recyclerview_movie)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.view_no_movies)
    RelativeLayout noMoviesLayout;
    @BindView(R.id.view_no_favorite)
    RelativeLayout noFavoriteLayout;

    private int page = 1;
    long currentVisiblePosition = 0;

    private MovieAdapter adapter;
    private final List<Movie> movieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(getContext(), recyclerView, movieList, (view1, position) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, movieList.get(position));
            startActivity(intent);
        });

        adapter.setLoadMore(() -> {
            // TODO: 5/28/2018 Fix the glith
            Log.i(TAG, "loadMore: ");
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page += 1;
                            loadMovies(page);

                            adapter.notifyDataSetChanged();
                            adapter.setLoading();
                        }
                    }, 1000);
                }
            });
        });
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isConnected()) {
            loadMovies(page);
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

        currentVisiblePosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        Log.i(TAG, "onPause: I am here and also this " + currentVisiblePosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Here");
        ((GridLayoutManager) recyclerView.getLayoutManager()).scrollToPosition((int) currentVisiblePosition);
        Log.i(TAG, "onResume: Position: " + currentVisiblePosition);
        currentVisiblePosition = 0;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @SuppressWarnings("ConstantConditions")
    private void loadMovies(int page) {
        Uri uri;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sort_by_options = preferences.getString(getString(R.string.sort), getString(R.string.sort_popular));

        if (sort_by_options.equals(getString(R.string.sort_popular))) {
            uri = Uri.parse(Constants.POPULAR_BASE_URL).buildUpon()
                    .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                    .appendQueryParameter(Constants.PAGE_APPEND, String.valueOf(page))
                    .build();


        } else {
            uri = Uri.parse(Constants.TOP_RATED_BASE_URL).buildUpon()
                    .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                    .build();
        }

        String uri_string = uri.toString();

        //Network functionality
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri_string, null, response -> {
            try {
                for (int i = 0; i < 20; i++) {
                    JSONObject result = response.getJSONArray("results").getJSONObject(i);
                    int movie_id = result.getInt("id");
                    String movie_title = result.getString("title");
                    String img_path = result.getString("poster_path");
                    String back_drop_path = result.getString("backdrop_path");
                    String overview = result.getString("overview");
                    String release_date = result.getString("release_date");
                    String vote_average = result.getString("vote_average");

                    final Movie movie = new Movie(movie_id, movie_title, img_path,
                            back_drop_path, overview, release_date, vote_average);
                    movieList.add(movie);

                    // TODO: 6/7/2018 Add movies to db for offline
                    //saving movies(hopefully it works)
//                    AppExecutors.getsInstance().diskIO().execute(() -> {
//                        database.movieDao().insertMovie(movie);
//                        Log.i(TAG, "run: Add movies to database");
//                    });
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e(TAG, "onResponse: " + e.getMessage());
            }
        }, error -> Log.e(TAG, "onErrorResponse: " + error.getMessage()));
        requestQueue.add(request);
    }

    private void showEmptyView() {
        if (recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            noMoviesLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noMoviesLayout.setVisibility(View.GONE);
        }


        //noinspection ConstantConditions
        Snackbar.make(getView(), "Cannot connect to the Internet", Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO: 6/13/2018 Come back
        outState.putParcelable(RECYCLER_POSITION, recyclerView.getLayoutManager().onSaveInstanceState());
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

























