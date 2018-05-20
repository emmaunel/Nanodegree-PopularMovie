package com.wordpress.ayo218.popularmovie;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener{
    private static  final int VISIBLE_THRESHOULS = 5;

    private GridLayoutManager gridLayoutManager;
    private boolean loading = false;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = gridLayoutManager.getItemCount();
        int lastVisibleItemPostition = gridLayoutManager.findLastVisibleItemPosition();

        boolean reachEnd = lastVisibleItemPostition + VISIBLE_THRESHOULS >= totalItemCount;
        if (!loading && totalItemCount > 0 && reachEnd){
            loading = true;
            onLoadMore();
        }
    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    public abstract void onLoadMore();
}
