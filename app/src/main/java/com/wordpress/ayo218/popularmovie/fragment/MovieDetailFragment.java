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

public class MovieDetailFragment extends Fragment {
    private Movie data;

    public MovieDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        data = getActivity().getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView movie_poster = view.findViewById(R.id.movie_detail_poster);
        TextView movie_title = view.findViewById(R.id.movie_title);
        TextView movie_rating = view.findViewById(R.id.text_movie_user_rating);
        TextView movie_release = view.findViewById(R.id.text_movie_release_date);
        TextView movie_details = view.findViewById(R.id.text_movie_overview);

        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(data.getPoster_path()))
                .into(movie_poster);

        movie_title.setText(data.getMovie_title());
        movie_rating.setText(data.getVote_average());
        movie_release.setText(data.getRelease_date());
        movie_details.setText(data.getOverview());
    }
}
