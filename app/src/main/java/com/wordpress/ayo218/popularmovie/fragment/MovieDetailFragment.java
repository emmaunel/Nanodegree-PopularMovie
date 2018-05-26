package com.wordpress.ayo218.popularmovie.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.adapter.ReviewsAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {
    @BindView(R.id.movie_detail_poster)
     ImageView movie_poster;
    @BindView(R.id.movie_title)
     TextView movie_title;
    @BindView(R.id.text_movie_user_rating)
     TextView movie_rating;
    @BindView(R.id.text_movie_release_date)
     TextView movie_release;
    @BindView(R.id.text_movie_overview)
     TextView movie_details;
    @BindView(R.id.movie_reviews_recyclerview)
    RecyclerView reviews_recyclerview;

    private final List<Review> reviewList = new ArrayList<>();
    ReviewsAdapter adapter;

    private Movie data;
    private static final String TAG = "MovieDetailFragment";


    // TODO: 5/25/2018 FIx the UI for the review and also into that little glitch when scrolling
    // TODO: 5/25/2018 Add clicklisterner to each review
    // TODO: 5/25/2018 Add a dark line for divider 
    // TODO: 5/25/2018 Add "Release Date" next to the date in the UI 
    public MovieDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //noinspection ConstantConditions
        data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        ButterKnife.bind(this, view);

        initReviews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(data.getPoster_path()))
                .into(movie_poster);

        movie_title.setText(data.getMovie_title());
        movie_rating.setText(data.getVote_average());
        movie_release.setText(data.getRelease_date());
        movie_details.setText(data.getOverview());
    }

    private void initReviews(){
        adapter = new ReviewsAdapter(getContext(), reviewList);
        reviews_recyclerview.setHasFixedSize(true);
        reviews_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews_recyclerview.setAdapter(adapter);
        fetchReviews();
    }

    private void fetchReviews() {
        Uri review_uri = Uri.parse(Constants.MOVIE_REVIEW_URL).buildUpon()
                .appendPath(String.valueOf(data.getMovie_id()))
                .appendPath(Constants.REVIEW_PATH)
                .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                .build();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, review_uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: " + response);
                        try{
                        JSONArray results = response.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            String review_author = jsonObject.getString("author");
                            String review_content = jsonObject.getString("content");

                            reviewList.add(new Review(review_author, review_content));
                        }
                        adapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(request);
    }


}
