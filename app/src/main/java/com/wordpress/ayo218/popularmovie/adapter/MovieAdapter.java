package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieImage.setImageResource(movies.get(position).getMovieImage());
        holder.movieName.setText(movies.get(position).getMovieTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_image)ImageView movieImage;
        @BindView(R.id.movie_name_txt) TextView movieName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
