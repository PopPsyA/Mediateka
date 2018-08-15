package com.ru.devit.mediateka.presentation.cinemalist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemalist.CinemaListModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.common.AbstractCinemaListAdapter;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemaListAdapter;
import com.ru.devit.mediateka.utils.pagination.PaginationScrollListener;

import java.util.List;

import javax.inject.Inject;

public class CinemaListFragment extends Fragment implements CinemaListPresenter.View, OnCinemaClickListener {

    public static final String TAB_POSITION_NAME = "tab_position";
    private static final int MENU_ITEM_HOW_TO_SHOW_CINEMA_LIST = 824;

    private RecyclerView mRecyclerViewCinemas;
    private SwipeRefreshLayout mSwipeRefresherLayout;
    private AbstractCinemaListAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem mMenuItemHowToShowCinemaList;

    @Inject CinemaListPresenter presenter;

    public static Fragment newInstance(String tabPosition){
        CinemaListFragment cinemaListFragment = new CinemaListFragment();
        Bundle args = new Bundle();
        args.putString(TAB_POSITION_NAME, tabPosition);
        cinemaListFragment.setArguments(args);
        return cinemaListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_cinema, container , false);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        initDagger();
        initViews(rootView);
        initPresenter();
        initAdapter();
        initRecyclerView(rootView);
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
                        .findViewById(R.id.iv_cinema_poster) ,
                getString(R.string.transition_cinema_poster_image));
        Intent intent = CinemaDetailsActivity.makeIntent(getContext(), cinemaId);
        ActivityCompat.startActivity(getContext() , intent , activityOptions.toBundle());
    }

    @Override
    public void showSmallCinemaListInOneRow(){
        changeAdapter(new SmallCinemaListAdapter(requireContext() ,this) ,
                R.drawable.ic_big_cinema_in_one_row);
    }

    @Override
    public void showBigCinemaListInOneRow(){
        changeAdapter(new CinemaListAdapter(this) ,
                R.drawable.ic_small_cinema_in_one_row);
    }

    @Override
    public void onCinemaClicked(int cinemaId, int viewHolderPosition) {
        presenter.onCinemaClicked(cinemaId , viewHolderPosition);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenuItemHowToShowCinemaList = menu
                .add(Menu.NONE, MENU_ITEM_HOW_TO_SHOW_CINEMA_LIST, Menu.NONE, getString(R.string.how_to_show_cinema_list))
                .setIcon(R.drawable.ic_small_cinema_in_one_row);
        mMenuItemHowToShowCinemaList.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_HOW_TO_SHOW_CINEMA_LIST){
            presenter.onMenuItemHowToShowCinemaListClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (adapter == null){
            adapter = new CinemaListAdapter(this);
        }
    }

    private void initRecyclerView(View rootView){
        mRecyclerViewCinemas = rootView.findViewById(R.id.rv_cinemas);
        mRecyclerViewCinemas.setLayoutManager(mLayoutManager);
        mRecyclerViewCinemas.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewCinemas.setAdapter(adapter);
        mRecyclerViewCinemas.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
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
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mSwipeRefresherLayout = view.findViewById(R.id.swipe_refresher_layout);
        mSwipeRefresherLayout.setColorSchemeResources(R.color.colorBlack , R.color.colorOrange , R.color.colorRed);
        mSwipeRefresherLayout.setOnRefreshListener(() -> presenter.loadCinemas());
    }

    @SuppressWarnings("ConstantConditions")
    private void initPresenter(){
        presenter.setView(this);
        presenter.setTabPositionName(getArguments().getString(TAB_POSITION_NAME));
        presenter.initialize();
        if (mSwipeRefresherLayout.isRefreshing()) presenter.loadCinemas();
    }

    private void initDagger(){
        if (presenter == null){
            MediatekaApp
                    .getComponentsManager()
                    .plusCinemaComponent()
                    .plusCinemaListComponent(new CinemaListModule())
                    .inject(this);
        }
    }

    private void changeAdapter(AbstractCinemaListAdapter newAdapter , @DrawableRes int icon){
        adapter = newAdapter;
        mRecyclerViewCinemas.setAdapter(adapter);
        mMenuItemHowToShowCinemaList.setIcon(icon);
    }
}
