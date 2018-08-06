package com.wordpress.ayo218.popularmovie.fragment;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.adapter.ReviewsAdapter;
import com.wordpress.ayo218.popularmovie.adapter.TrailersAdapter;
import com.wordpress.ayo218.popularmovie.model.Movie;
import com.wordpress.ayo218.popularmovie.model.Review;
import com.wordpress.ayo218.popularmovie.model.Trailers;
import com.wordpress.ayo218.popularmovie.utils.Constants;
import com.wordpress.ayo218.popularmovie.utils.ItemOffsetDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = "MovieDetailFragment";

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
    @BindView(R.id.card_movie_review)
    CardView reviewCardView;

    @BindView(R.id.movie_trailer_recyclerview)
    RecyclerView trailers_recyclerview;
    @BindView(R.id.card_movie_trailers)
    CardView trailersCardView;


    private Movie data;
    private ReviewsAdapter adapter;
    private TrailersAdapter trailersAdapter;
    private final List<Review> reviewList = new ArrayList<>();
    private final List<Trailers> trailersList = new ArrayList<>();


    // TODO: 5/25/2018 FIx the UI for the review and also into that little glitch when scrolling

    public MovieDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //noinspection ConstantConditions
        data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        ButterKnife.bind(this, view);
        // TODO: 8/6/2018 Trying our slide transition
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setSlideEdge(Gravity.BOTTOM);
            slide.setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.linear_out_slow_in));
            getActivity().getWindow().setEnterTransition(slide);
        }

        initReviews();
        initTrailers();
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
        movie_release.setText(String.format("Release Date: %s", data.getRelease_date()));
        movie_details.setText(data.getOverview());
    }

    private void initReviews(){
        adapter = new ReviewsAdapter(getContext(), reviewList, (view, position) -> reviewClicked(position));
        reviews_recyclerview.setHasFixedSize(true);
        reviews_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews_recyclerview.setAdapter(adapter);
        fetchReviews();
    }

    private void initTrailers(){
        trailersAdapter = new TrailersAdapter(getContext(), trailersList, (view, position) -> movieClicked(position));
        trailers_recyclerview.setHasFixedSize(true);
        trailers_recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trailers_recyclerview.setItemAnimator(new DefaultItemAnimator());
        trailers_recyclerview.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.trailer_offset));
        trailers_recyclerview.setAdapter(trailersAdapter);
        fetchTrailers();
    }

    private void fetchReviews() {
        Uri review_uri = Uri.parse(Constants.MOVIE_URL).buildUpon()
                .appendPath(String.valueOf(data.getMovie_id()))
                .appendPath(Constants.REVIEW_PATH)
                .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                .build();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, review_uri.toString(), null, response -> {
            Log.e(TAG, "onResponse: " + response);
            try{
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                String review_author = jsonObject.getString("author");
                String review_content = jsonObject.getString("content");
                String review_url = jsonObject.getString("url");

                reviewList.add(new Review(review_author, review_content, review_url));
            }
            adapter.notifyDataSetChanged();
            updateReviewCard();
            }catch (JSONException e){
                Log.e(TAG, "onResponse: " + e.getMessage());
            }
        }, error -> Log.e(TAG, "onErrorResponse: " + error.getMessage()));
        queue.add(request);
    }

    private void fetchTrailers(){
        Uri trailer_uri = Uri.parse(Constants.MOVIE_URL).buildUpon()
                .appendEncodedPath(String .valueOf(data.getMovie_id()))
                .appendEncodedPath(Constants.TRAILER_PATH)
                .appendQueryParameter(Constants.API_APPEND, Constants.API_KEY)
                .build();
        Log.i(TAG, "fetchTrailers: " + trailer_uri);

        RequestQueue trailer_queue = Volley.newRequestQueue(getContext());
        
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, trailer_uri.toString(), null, response -> {
            try{
                JSONArray trailers_results = response.getJSONArray("results");

                for (int i = 0; i < trailers_results.length(); i++) {
                    JSONObject object = trailers_results.getJSONObject(i);
                    String id = object.getString("id");
                    String key = object.getString("key");
                    String name = object.getString("name");

                    trailersList.add(new Trailers(id, key, name));
                }
                trailersAdapter.notifyDataSetChanged();
                updateTrailerCard();
            }catch (JSONException e){
                Log.e(TAG, "onResponse: " +  e.getMessage());
            }
        }, error -> Log.e(TAG, "onErrorResponse: " + error.getMessage()));

        trailer_queue.add(request);
    }

    private void updateReviewCard(){
        if (adapter == null || adapter.getItemCount() == 0) {
            reviewCardView.setVisibility(View.GONE);
        } else {
            reviewCardView.setVisibility(View.VISIBLE);
        }
    }

    private void updateTrailerCard(){
        if (trailersAdapter == null || trailersAdapter.getItemCount() == 0) {
            trailersCardView.setVisibility(View.GONE);
        } else{
            trailersCardView.setVisibility(View.VISIBLE);
        }
    }

    private void movieClicked(int position){
        Trailers trailers = trailersAdapter.getItem(position);
        if (trailers != null){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailers.getTrailer_key())));
        }
    }

    private void reviewClicked(int position){
        Review review = adapter.getItem(position);
        if (review != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(reviewList.get(position).getReivew_url())));
        }
    }
}
