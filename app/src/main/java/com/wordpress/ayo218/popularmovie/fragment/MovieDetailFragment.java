package com.wordpress.ayo218.popularmovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {
    @BindView(R.id.movie_detail_poster) ImageView movie_poster;
    @BindView(R.id.movie_title) TextView movie_title;
    @BindView(R.id.text_movie_user_rating) TextView movie_rating;
    @BindView(R.id.text_movie_release_date) TextView movie_release;
    @BindView(R.id.text_movie_overview) TextView movie_details;
    private Movie data;

    public MovieDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(data.getPoster_path()))
                .into(movie_poster);

        movie_title.setText(data.getMovie_title());
        movie_rating.setText(data.getVote_average());
        movie_release.setText(data.getRelease_date());
        movie_details.setText(data.getOverview());
    }
}
