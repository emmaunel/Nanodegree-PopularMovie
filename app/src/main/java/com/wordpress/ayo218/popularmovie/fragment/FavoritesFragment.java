package com.wordpress.ayo218.popularmovie.fragment;

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

import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.activity.DetailActivity;
import com.wordpress.ayo218.popularmovie.adapter.MovieAdapter;
import com.wordpress.ayo218.popularmovie.database.favorites.FavoriteDatabase;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment {

    @BindView(R.id.recyclerview_movie)
    RecyclerView recyclerView;

    private MovieAdapter adapter;

    private FavoriteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        database = FavoriteDatabase.getsInstance(getContext());

        // TODO: 6/6/2018 Add Listerner to each movie
        // FIXME: 6/6/2018 Make sure a movie is not added twice
//        adapter.setFavorites(database.favoriteDao().loadFavorite());

        List<Movie> movieList = database.favoriteDao().loadFavorite();
        adapter = new MovieAdapter(getContext(), movieList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, movieList.get(position));
                startActivity(intent);
            }
        });

        adapter.setFavorites(database.favoriteDao().loadFavorite());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

    }
}
