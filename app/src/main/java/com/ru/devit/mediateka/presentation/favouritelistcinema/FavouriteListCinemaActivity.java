package com.ru.devit.mediateka.presentation.favouritelistcinema;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemafavourite.CinemaFavouriteListModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.common.RecyclerItemTouchHelper;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemaListAdapter;

import java.util.List;

import javax.inject.Inject;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class FavouriteListCinemaActivity extends BaseActivity implements FavouriteListCinemaPresenter.View, RecyclerItemTouchHelper.Callback , CinemaSortingDialog.OnDialogItemClicked {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewFavouriteListCinema;
    private SmallCinemaListAdapter adapter;
    private CoordinatorLayout mCoordinatorLayout;
    private CinemaSortingDialog mCinemaSortingDialog;
    private TextView mEmptyMessageTextView;

    private static final int MENU_CLEAR_FAVOURITE_LIST = 23;
    private static final int MENU_SORT_FAVOURITE_LIST = 27;

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
    public void showDetailedCinema(int cinemaId , int viewHolderPos){
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this ,
                mRecyclerViewFavouriteListCinema
                        .findViewHolderForAdapterPosition(viewHolderPos)
                        .itemView
                        .findViewById(R.id.iv_actor_detail_cinema_poster) ,
                getString(R.string.transition_cinema_poster_image)
        );
        Intent intent = CinemaDetailsActivity.makeIntent(this, cinemaId);
        ActivityCompat.startActivity(this , intent , activityOptions.toBundle());
    }

    @Override
    public void showUndoAction(String cinemaTitle , Cinema deletedCinema , int deletedIndex){
        Snackbar.make(mCoordinatorLayout , getString(R.string.message_removed_cinema , cinemaTitle) , LENGTH_LONG)
                .setAction(getString(R.string.undo) , v -> {
                    presenter.onUndoClicked(deletedCinema , deletedIndex);
                    adapter.restoreCinema(deletedCinema , deletedIndex);
                    if (mEmptyMessageTextView.getVisibility() == View.VISIBLE){
                        mEmptyMessageTextView.setVisibility(View.GONE);
                    }
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
    public void onDialogItemClicked(int position) {
        mCinemaSortingDialog.setPosition(position);
        presenter.onCinemaSortingDialogItemClicked(position);
    }

    @Override
    public void showEmptyScreen(){
        mEmptyMessageTextView.setText(getString(R.string.message_empty_favourite_cinema_list));
        mEmptyMessageTextView.setGravity(Gravity.CENTER);
        mEmptyMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP , 24);
        mEmptyMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE , MENU_CLEAR_FAVOURITE_LIST , Menu.NONE , getString(R.string.message_clear_favourite_list));
        menu.add(Menu.NONE , MENU_SORT_FAVOURITE_LIST , Menu.NONE , getString(R.string.sort_favourite_list))
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setIcon(R.drawable.ic_sort);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_CLEAR_FAVOURITE_LIST : {
                presenter.onMenuClearFavouriteListClicked();
                adapter.clear();
                return true;

            }
            case MENU_SORT_FAVOURITE_LIST : {
                mCinemaSortingDialog.show(getSupportFragmentManager() , CinemaSortingDialog.class.getSimpleName());
                return true;
            }

            default : return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSavedSortedPosition(int savedPosition){
        mCinemaSortingDialog = CinemaSortingDialog.newInstance();
        mCinemaSortingDialog.setPosition(savedPosition);
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
                (cinemaId , viewHolderPos) -> presenter.onCinemaClicked(cinemaId , viewHolderPos) ,
                true ,
                R.color.colorWhite ,
                false);
        mRecyclerViewFavouriteListCinema.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewFavouriteListCinema.setAdapter(adapter);
        mCoordinatorLayout = findViewById(R.id.cl_favourite_list_cinema);
        mCinemaSortingDialog = new CinemaSortingDialog();
        mEmptyMessageTextView = new TextView(this);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mEmptyMessageTextView.setLayoutParams(params);
        mCoordinatorLayout.addView(mEmptyMessageTextView);
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
