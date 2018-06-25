package com.ru.devit.mediateka.presentation.favouritelistcinema;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemafavourite.CinemaFavouriteListModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.common.RecyclerItemTouchHelper;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemaListAdapter;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemaViewHolder;

import java.util.List;

import javax.inject.Inject;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class FavouriteListCinemaActivity extends BaseActivity implements FavouriteListCinemaPresenter.View, RecyclerItemTouchHelper.Callback {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewFavouriteListCinema;
    private SmallCinemaListAdapter adapter;
    private CoordinatorLayout mCoordinatorLayout;

    private static final int MENU_CLEAR_FAVOURITE_LIST = 23;

    @Inject FavouriteListCinemaPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favourite_list_cinema;
    }

    @Override
    public void showFavouriteListCinema(List<Cinema> cinemaList) {
        presenter.setCinemaList(cinemaList);
        adapter.addAll(cinemaList);
    }

    @Override
    public void showDetailedCinema(int cinemaId){
        Intent intent = CinemaDetailsActivity.makeIntent(this , cinemaId);
        startActivity(intent);
    }

    @Override
    public void showUndoAction(String cinemaTitle , Cinema deletedCinema , int deletedIndex){
        Snackbar.make(mCoordinatorLayout , getString(R.string.message_removed_cinema , cinemaTitle) , LENGTH_LONG)
                .setAction(getString(R.string.undo) , v -> {
                    presenter.onUndoClicked(deletedCinema , deletedIndex);
                    adapter.restoreCinema(deletedCinema , deletedIndex);
                })
                .show();
    }

    @Override
    public void onSwiped(int adapterPos) {
        presenter.onCinemaSwiped(adapterPos);
        adapter.removeCinema(adapterPos);
    }

    @Override
    public void showSuccessfullyFavouriteListCleared(){
        Snackbar.make(mRecyclerViewFavouriteListCinema ,
                getString(R.string.message_successfully_favourite_cinema_list_cleared) ,
                LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE , MENU_CLEAR_FAVOURITE_LIST , Menu.NONE , getString(R.string.message_clear_favourite_list));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_CLEAR_FAVOURITE_LIST){
            presenter.onMenuClearFavouriteListClicked();
            adapter.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        adapter = new SmallCinemaListAdapter(this ,
                (cinemaId , viewHolderPos) -> presenter.onCinemaClicked(cinemaId) ,
                true ,
                R.color.colorWhite ,
                false);
        mRecyclerViewFavouriteListCinema.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewFavouriteListCinema.setAdapter(adapter);
        mCoordinatorLayout = findViewById(R.id.cl_favourite_list_cinema);
        new ItemTouchHelper(new RecyclerItemTouchHelper(ItemTouchHelper.LEFT , this)).attachToRecyclerView(mRecyclerViewFavouriteListCinema);
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
