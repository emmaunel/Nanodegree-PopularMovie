package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private final Context context;
    private final List<Movie> movieList;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> movieList, OnItemClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get()
                .load(Constants.BASE_IMAGE_URL.concat(movieList.get(position).getPoster_path()))
                .into(holder.movie_image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_image)
        ImageView movie_image;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

     }
}
