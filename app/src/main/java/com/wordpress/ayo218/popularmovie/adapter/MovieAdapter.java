package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private static final String TAG = "MovieAdapter";
    private Context context;
    private List<Movie> movieList;
    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";


    //EXTRAS
    public static final String MOVIE_TITLE_EXTRA = "movie_title";
    public static final String MOVIE_OVERVIEW_EXTRA = "movie_overview";
    public static final String MOVIE_RELEASE_DATE_EXTRA = "release_date";
    public static final String MOVIE_VOTE_AVERAGE_EXTRA = "vote_average";
    public static final String MOVIE_OBJECT = "movie";

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.get()
                .load(POSTER_PATH.concat(movieList.get(position).getPoster_path()))
                .into(holder.movie_image);
        Log.i(TAG, "The Image url is " + movieList.get(position).getPoster_path());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movie_image;


        public ViewHolder(View itemView) {
            super(itemView);
            movie_image = itemView.findViewById(R.id.movie_image);

        }

        void bindImage(Uri image){
            Picasso.with(context)
                    .load(image)
                    .into(movie_image);
        }

     }

     public void setMovieList(List<Movie> movieList){this.movieList = movieList;}
}
