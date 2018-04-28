package com.wordpress.ayo218.popularmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ayo on 4/27/2018.
 */

public class MovieFragment extends Fragment {

    // TODO: 4/28/2018 Build Layout
    // TODO: 4/28/2018 Fill with dummy data
    // TODO: 4/28/2018 is scattered gridview available?

//    @BindView(R.id.recyclerview_fragment_movie)
//    RecyclerView recyclerView;
//    MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
//        ButterKnife.bind(this, view);
//        fillGridView();
        return view;
    }

//    private void fillGridView() {
//        ArrayList<Movie> movies = new ArrayList<>();
//        movies.add(new Movie("Games", R.drawable.ic_launcher_background));
//        movies.add(new Movie("ames", R.drawable.ic_launcher_background));
//        movies.add(new Movie("mes", R.drawable.ic_launcher_background));
//        movies.add(new Movie("Ges", R.drawable.ic_launcher_background));
//        movies.add(new Movie("Gas", R.drawable.ic_launcher_background));
//        movies.add(new Movie("Game", R.drawable.ic_launcher_background));
//        movies.add(new Movie("Gamedds", R.drawable.ic_launcher_background));
//
//        adapter = new MovieAdapter(getContext(), movies);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.setAdapter(adapter);
//    }
}
