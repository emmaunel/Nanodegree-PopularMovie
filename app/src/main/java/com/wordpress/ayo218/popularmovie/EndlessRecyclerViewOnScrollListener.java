package com.wordpress.ayo218.popularmovie;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener{
    private int VISIBLE_THRESHOULS = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private int startingPageIndex = 0;


    private GridLayoutManager gridLayoutManager;
    private boolean loading = true;

//    public EndlessRecyclerViewOnScrollListener(int VISIBLE_THRESHOULS){
//        this.VISIBLE_THRESHOULS = VISIBLE_THRESHOULS;
//    }
//
//    public EndlessRecyclerViewOnScrollListener(int VISIBLE_THRESHOULS, int startingPageIndex){
//        this.VISIBLE_THRESHOULS = VISIBLE_THRESHOULS;
//        this.startingPageIndex = startingPageIndex;
//        this.currentPage = startingPageIndex;
//    }

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
