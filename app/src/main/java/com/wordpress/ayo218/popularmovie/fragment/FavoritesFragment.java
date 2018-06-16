package com.wordpress.ayo218.popularmovie.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.activity.DetailActivity;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.utils.MainViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment {

    @BindView(R.id.recyclerview_movie)
    RecyclerView recyclerView;
    @BindView(R.id.view_no_favorite)
    RelativeLayout noFavoriteLayout;

    private MovieAdapter adapter;


    // TODO: 6/6/2018 Add empty view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MainViewHolder viewHolder = ViewModelProviders.of(this).get(MainViewHolder.class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (viewHolder.getMoviesList() == null){
            showEmptyView();
        }
        viewHolder.getMoviesList().observe(this, movies -> {
            adapter = new MovieAdapter(getContext(), movies, (view1, position) -> {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, movies.get(position));
                startActivity(intent);
            });
            adapter.setFavorites(movies);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        });
    }

    private void showEmptyView() {
        noFavoriteLayout.setVisibility(View.VISIBLE);
    }


}
