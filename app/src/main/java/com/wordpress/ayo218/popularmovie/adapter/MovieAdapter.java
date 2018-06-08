package com.wordpress.ayo218.popularmovie.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.wordpress.ayo218.popularmovie.Constants;
import com.wordpress.ayo218.popularmovie.Interface.LoadMoreInterface;
import com.wordpress.ayo218.popularmovie.Interface.OnItemClickListener;
import com.wordpress.ayo218.popularmovie.R;
import com.wordpress.ayo218.popularmovie.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Movie> movieList;
    private OnItemClickListener listener;
    private LoadMoreInterface loadMore;

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public MovieAdapter(Context context, List<Movie> movieList, final OnItemClickListener listener){
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    public MovieAdapter(Context context, RecyclerView recyclerView, List<Movie> movieList, final OnItemClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;

        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null) {
                        loadMore.loadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setLoading(){
        isLoading = false;
    }
    @Override
    public int getItemViewType(int position) {
        return movieList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(view1 -> listener.onItemClick(view1, viewHolder.getAdapterPosition()));
            return viewHolder;
        } else if (viewType == VIEW_TYPE_LOADING){
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            Picasso.get()
                    .load(Constants.BASE_IMAGE_URL.concat(movieList.get(position).getPoster_path()))
                    .fit()
                    .into(movieViewHolder.movie_image);
        } else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setLoadMore(LoadMoreInterface loadMore) {
        this.loadMore = loadMore;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_image)
        ImageView movie_image;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFavorites(List<Movie> favorites){
        movieList = favorites;
        notifyDataSetChanged();
    }
}
