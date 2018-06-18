package com.ru.devit.mediateka.presentation.favouritelistcinema;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemafavourite.CinemaFavouriteListModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemaListAdapter;

import java.util.List;

import javax.inject.Inject;

public class FavouriteListCinemaActivity extends BaseActivity implements FavouriteListCinemaPresenter.View {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewFavouriteListCinema;
    private SmallCinemaListAdapter adapter;

    @Inject FavouriteListCinemaPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favourite_list_cinema;
    }

    @Override
    public void showFavouriteListCinema(List<Cinema> cinemaList) {
        adapter.addAll(cinemaList);
    }

    @Override
    public void showDetailedCinema(int cinemaId){
        Intent intent = CinemaDetailsActivity.makeIntent(this , cinemaId);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        mProgressBar = findViewById(R.id.pb_small_cinemas);
        mRecyclerViewFavouriteListCinema = findViewById(R.id.rv_small_cinemas);
        adapter = new SmallCinemaListAdapter((cinemaId , viewHolderPos) -> presenter.onCinemaClicked(cinemaId));
        mRecyclerViewFavouriteListCinema.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewFavouriteListCinema.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    @Override
    protected void initToolbar(){
        super.initToolbar();
        if (getSupportActionBar() != null){
            changeStatusBarColor(R.color.colorDarkPurple);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initDagger() {
        MediatekaApp.getComponentsManager()
                .plusCinemaComponent()
                .plusCinemaFavouriteListComponent(new CinemaFavouriteListModule())
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearCinemaComponent();
        super.onDestroy();
    }
}
