package com.ru.devit.mediateka.presentation.main;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.data.ConnectionReceiver;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaListFragment;
import com.ru.devit.mediateka.presentation.popularactors.PopularActors;
import com.ru.devit.mediateka.presentation.search.SearchActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainPresenter.View, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private Snackbar mSnackbar;
    private ConnectionReceiver mConnectionReceiver;

    private static final int MAX_TABS = 3;
    public static final int ACTUAL_CINEMAS_TAB_POSITION = 0;
    public static final int TOP_RATED_CINEMAS_TAB_POSITION = 1;
    public static final int UP_COMING_CINEMAS_TAB_POSITION = 2;

    @Inject MainPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        mNavigationView.setNavigationItemSelectedListener(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mNavigationView.setNavigationItemSelectedListener(null);
        super.onStop();
    }

    @Override
    public void startToListenInternetConnection() {
        registerReceiver(mConnectionReceiver , new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        ((MediatekaApp)getApplicationContext()).setConnectionListener(presenter);
    }

    @Override
    public void showNetworkError(){
        mSnackbar = Snackbar.make(mViewPager, getString(R.string.message_network_error), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.message_retry) ,
                        v -> presenter.onRetryButtonClicked(mConnectionReceiver.isInternetConnected(MainActivity.this)));
        View view = mSnackbar.getView();
        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        mSnackbar.show();
    }

    @Override
    public void hideNetworkError(){
        mSnackbar.dismiss();
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
        mNavigationView = findViewById(R.id.nav_view);
        mDrawer = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this , mDrawer , mToolbar , R.string.nav_open , R.string.nav_close);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(MAX_TABS);
        setUpViewPager(mViewPager);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mDrawer.addDrawerListener(mToggle);
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
                showToast(comingSoon);
                break;
            }
            case R.id.nav_popular_actors: {
                showToast(comingSoon);
                //startActivity(new Intent(this , PopularActors.class));
                break;
            }
            case R.id.nav_schedules : {
                showToast(comingSoon);
                break;
            }
            case R.id.nav_settings : {
                showToast(comingSoon);
                break;
            }
            case R.id.nav_advanced_search : {
                showToast(comingSoon);
                break;
            }
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mConnectionReceiver);
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

    private void setUpViewPager(ViewPager viewPager){
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CinemaListFragment.newInstance(ACTUAL_CINEMAS_TAB_POSITION) , getString(R.string.actual_cinemas));
        adapter.addFragment(CinemaListFragment.newInstance(TOP_RATED_CINEMAS_TAB_POSITION) , getString(R.string.by_rating));
        adapter.addFragment(CinemaListFragment.newInstance(UP_COMING_CINEMAS_TAB_POSITION) , getString(R.string.coming_soon));
        viewPager.setAdapter(adapter);
    }

}
