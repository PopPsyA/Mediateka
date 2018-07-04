package com.ru.devit.mediateka.presentation.search;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.presentation.common.ViewPagerAdapter;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemasFragment;
import com.ru.devit.mediateka.utils.AnimUtils;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity implements SearchPresenter.View, TabLayout.OnTabSelectedListener, TextWatcher {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private EditText mSearchField;
    private AppBarLayout mAppBar;

    private SmallCinemasFragment mCinemasFragment;
    private ActorsFragment mActorsFragment;

    @Inject SearchPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTabLayout.addOnTabSelectedListener(this);
        mSearchField.addTextChangedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        presenter.onTabSelected(tab.getPosition());
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String query = charSequence.toString();
        presenter.onTextChanged(query);

        //TODO make icon exit
    }

    @Override
    public void onCinemaTabSelected() {
        mSearchField.setHint(getString(R.string.search_cinemas));
        dynamicallyChangeColor(R.color.colorPurple , R.color.colorDarkPurple);
    }

    @Override
    public void onActorTabSelected() {
        mSearchField.setHint(getString(R.string.search_actors));
        dynamicallyChangeColor(R.color.colorLightBlue , R.color.colorDarkBlue);
    }

    @Override
    public void textFromCinemaTab(String query) {
        mCinemasFragment.textFromSearchField(query);
    }

    @Override
    public void textFromActorTab(String query) {
        mActorsFragment.textFromSearchField(query);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void initViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mSearchField = findViewById(R.id.et_search_field);
        mAppBar = findViewById(R.id.search_app_bar);
        setUpViewPager(mViewPager);
        changeStatusBarColor(R.color.colorDarkPurple);
    }

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initDagger() {
        MediatekaApp.getComponentsManager()
                .getAppComponent()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        mSearchField.addTextChangedListener(null);
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void dynamicallyChangeColor(@ColorRes int color , @ColorRes int statusBarColor) {
        int parsedColor = ContextCompat.getColor(this , color);
        AnimUtils.startRevealAnimationWithOutVisibility(mAppBar);
        changeStatusBarColor(statusBarColor);
        mTabLayout.setBackgroundColor(parsedColor);
        getToolbar().setBackgroundColor(parsedColor);
    }

    private void setUpViewPager(ViewPager viewPager){
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mCinemasFragment = SmallCinemasFragment.newInstance(true);
        mActorsFragment = ActorsFragment.newInstance();
        adapter.addFragment(mCinemasFragment , getString(R.string.cinemas));
        adapter.addFragment(mActorsFragment , getString(R.string.actors));
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
