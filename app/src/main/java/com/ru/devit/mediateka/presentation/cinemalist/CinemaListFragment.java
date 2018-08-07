package com.ru.devit.mediateka.presentation.cinemalist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemalist.CinemaListModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.utils.pagination.PaginationScrollListener;

import java.util.List;

import javax.inject.Inject;

public class CinemaListFragment extends Fragment implements CinemaListPresenter.View{

    public static final String TAB_POSITION = "tab_position";

    private RecyclerView mRecyclerViewCinemas;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefresherLayout;
    private CinemaListAdapter adapter;

    @Inject CinemaListPresenter presenter;

    public static Fragment newInstance(int tabPosition){
        CinemaListFragment cinemaListFragment = new CinemaListFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, tabPosition);
        cinemaListFragment.setArguments(args);
        return cinemaListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_cinema, container , false);
        initDagger();
        initViews(rootView);
        initPresenter();
        initAdapter();
        initRecyclerView(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void showLoading() {
        mSwipeRefresherLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefresherLayout.setRefreshing(false);
    }

    @Override
    public void showCinemas(List<Cinema> cinemaEntities) {
        adapter.addAll(cinemaEntities);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void openCinemaDetails(int cinemaId , int viewHolderPosition) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity() ,
                mRecyclerViewCinemas
                        .findViewHolderForAdapterPosition(viewHolderPosition)
                        .itemView
                        .findViewById(R.id.iv_poster) ,
                getString(R.string.transition_cinema_poster_image));
        Intent intent = CinemaDetailsActivity.makeIntent(getContext(), cinemaId);
        ActivityCompat.startActivity(getContext() , intent , activityOptions.toBundle());
    }

    @Override
    public void onPopularTabSelected() {
        // Im thinking...
    }

    @Override
    public void onTopRatedTabSelected() {
        // Im thinking...
    }

    @Override
    public void onUpComingTabSelected() {
        // Im thinking...
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearCinemaComponent();
        super.onDestroy();
    }

    public void scrollToFirstPosition() {
        int firstPosition = 0;
        mRecyclerViewCinemas.smoothScrollToPosition(firstPosition);
    }

    private void initAdapter() {
        adapter = new CinemaListAdapter((cinemaId, viewHolderPosition) -> presenter.onCinemaClicked(cinemaId , viewHolderPosition));
    }

    private void initRecyclerView(View rootView){
        mRecyclerViewCinemas = rootView.findViewById(R.id.rv_cinemas);
        mRecyclerViewCinemas.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewCinemas.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewCinemas.setAdapter(adapter);
        mRecyclerViewCinemas.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.onLoadNextPage();
            }

            @Override
            protected boolean isLastPage() {
                return presenter.isLastPage();
            }
        });
    }

    private void initViews(View view){
        mLinearLayoutManager = new LinearLayoutManager(view.getContext() , LinearLayoutManager.VERTICAL , false);
        mSwipeRefresherLayout = view.findViewById(R.id.swipe_refresher_layout);
        mSwipeRefresherLayout.setColorSchemeResources(R.color.colorBlack , R.color.colorOrange , R.color.colorRed);
        mSwipeRefresherLayout.setOnRefreshListener(() -> presenter.loadCinemas());
    }

    @SuppressWarnings("ConstantConditions")
    private void initPresenter(){
        presenter.setView(this);
        presenter.setTabPosition(getArguments().getInt(TAB_POSITION));
        presenter.initialize();
        if (mSwipeRefresherLayout.isRefreshing()) presenter.loadCinemas();
    }

    private void initDagger(){
        MediatekaApp
                .getComponentsManager()
                .plusCinemaComponent()
                .plusCinemaListComponent(new CinemaListModule())
                .inject(this);
    }
}
