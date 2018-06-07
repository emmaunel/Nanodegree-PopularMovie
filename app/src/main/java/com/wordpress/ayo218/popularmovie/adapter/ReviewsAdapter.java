package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final Context context;
    private final List<Review> movieReviews;
    private final OnItemClickListener listener;

    public ReviewsAdapter(Context context, List<Review> movieReviews, OnItemClickListener listener) {
        this.context = context;
        this.movieReviews = movieReviews;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_review, parent, false);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: 5/25/2018 Add empty text saying NO REVIEWS YET
        holder.review_author.setText(movieReviews.get(position).getAuthor());
        holder.review_content.setText(movieReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_review_author)
        TextView review_author;
        @BindView(R.id.movie_review_content)
        TextView review_content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
