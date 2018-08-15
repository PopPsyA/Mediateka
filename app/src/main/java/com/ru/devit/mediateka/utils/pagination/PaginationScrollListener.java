package com.ru.devit.mediateka.utils.pagination;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager layoutManager;

    public PaginationScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = 0;
        final int OFFSET = 7; // 7 cinemas before we start loadMoreItems
        if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        }

        Log.d("lolkek" , "visible " + visibleItemCount);
        Log.d("lolkek" , "total " + totalItemCount);
        Log.d("lolkek" , "firstVisible " + firstVisibleItemPosition);

        if (!isLastPage()){
            if (((visibleItemCount + OFFSET) + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0){
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    protected abstract boolean isLastPage();
}
