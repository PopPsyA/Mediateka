package com.ru.devit.mediateka.presentation.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.data.ConnectionReceiver;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaTab;
import com.ru.devit.mediateka.presentation.common.OnTabSelectedListener;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaListFragment;
import com.ru.devit.mediateka.presentation.favouritelistcinema.FavouriteListCinemaActivity;
import com.ru.devit.mediateka.presentation.popularactors.PopularActorsActivity;
import com.ru.devit.mediateka.presentation.search.SearchActivity;
import com.ru.devit.mediateka.presentation.settings.SettingsActivity;
import com.ru.devit.mediateka.utils.AnimUtils;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainPresenter.View, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private ViewPagerAdapter mViewPagerAdapter;
    private FloatingActionButton mFloatingActionBarScrollUp;
    private Snackbar mSnackbar;
    private ConnectionReceiver mConnectionReceiver;

    private static final int MAX_TABS = 3;

    @Inject MainPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mFloatingActionBarScrollUp.setOnClickListener(v -> presenter.onFABScrollUpClicked());
        mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabSelected(tab.getPosition());
            }
        });
        super.onStart();
    }

    @Override
    protected void onStop() {
        mNavigationView.setNavigationItemSelectedListener(null);
        mFloatingActionBarScrollUp.setOnClickListener(null);
        super.onStop();
    }

    @Override
    public void startToListenInternetConnection() {
        registerReceiver(mConnectionReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        ((MediatekaApp)getApplicationContext()).setConnectionListener(presenter);
    }

    @Override
    public void showNetworkError(){
        if (isAboveLollipop()){
            mSnackbar = Snackbar.make(mViewPager, getString(R.string.message_network_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.message_retry) ,
                            v -> presenter.onRetryButtonClicked(mConnectionReceiver.isInternetConnected(MainActivity.this)));
            View view = mSnackbar.getView();
            TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(5);
            mSnackbar.show();
        } else {
            Toast.makeText(this , getString(R.string.message_network_error) , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void scrollToFirstPosition(){
        CinemaListFragment fragment = (CinemaListFragment) mViewPagerAdapter.getItem(mViewPager.getCurrentItem());
        fragment.scrollToFirstPosition();
    }

    @Override
    public void hideNetworkError(){
        mSnackbar.dismiss();
    }

    @Override
    public void onPopularTabSelected() {
        dynamicallyChangeColor(R.color.colorPurple , R.color.colorDarkPurple);
    }

    @Override
    public void onTopRatedTabSelected() {
        dynamicallyChangeColor(R.color.colorOrange , R.color.colorDarkOrange);
    }

    @Override
    public void onUpComingTabSelected() {
        dynamicallyChangeColor(R.color.colorRed , R.color.colorDarkRed);
    }

    @Override
    protected void initDagger() {
        MediatekaApp.getComponentsManager()
                .getAppComponent()
                .inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initViews() {
        mToolbar = getToolbar();
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.app_name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mAppBar = findViewById(R.id.main_app_bar);
        changeStatusBarColor(R.color.colorPurple);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawer = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this , mDrawer , mToolbar , R.string.nav_open , R.string.nav_close);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(MAX_TABS);
        setUpViewPager(mViewPager);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mDrawer.addDrawerListener(mToggle);
        mFloatingActionBarScrollUp = findViewById(R.id.fab_up_list_to_start_position);
        mConnectionReceiver = new ConnectionReceiver();
    }

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search : {
                Intent intent = new Intent(this , SearchActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String comingSoon = "Coming soon";
        switch (id){
            case R.id.nav_cinemas : {
                navigateToMainActivity(this);
                break;
            }
            case R.id.nav_popular_actors: {
                navigateTo(PopularActorsActivity.class);
                break;
            }
            case R.id.nav_schedules : {
                showToast(comingSoon);
                break;
            }
            case R.id.nav_settings : {
                navigateTo(SettingsActivity.class);
                break;
            }
            case R.id.nav_advanced_search : {
                showToast(comingSoon);
                break;
            }
            case R.id.nav_favourite_list_cinema: {
                navigateTo(FavouriteListCinemaActivity.class);
            }
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mConnectionReceiver);
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void dynamicallyChangeColor(@ColorRes int color , @ColorRes int statusBarColor) {
        int parsedColor = ContextCompat.getColor(this , color);
        AnimUtils.startRevealAnimationWithOutVisibility(mAppBar);
        changeStatusBarColor(statusBarColor);
        mTabLayout.setBackgroundColor(parsedColor);
        mToolbar.setBackgroundColor(parsedColor);
        mFloatingActionBarScrollUp.setBackgroundTintList(ColorStateList.valueOf(parsedColor));
    }

    private void setUpViewPager(ViewPager viewPager){
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(CinemaListFragment.newInstance(CinemaTab.POPULAR.name()) , getString(R.string.actual_cinemas));
        mViewPagerAdapter.addFragment(CinemaListFragment.newInstance(CinemaTab.TOP_RATED.name()) , getString(R.string.by_rating));
        mViewPagerAdapter.addFragment(CinemaListFragment.newInstance(CinemaTab.UP_COMING.name()) , getString(R.string.coming_soon));
        viewPager.setAdapter(mViewPagerAdapter);
    }

    private void navigateTo(Class<?> activityClass){
        startActivity(new Intent(this , activityClass));
    }
}
