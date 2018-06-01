package com.ru.devit.mediateka.presentation.posterslider;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.presentation.base.BaseActivity;
import com.ru.devit.mediateka.presentation.common.OnPageScrolled;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PosterSliderActivity extends BaseActivity implements PosterSliderPresenter.View {

    private static final String POSTERS = "cinema_posters";
    private static final String TRANSITION_NAME = "transition_name";

    private ViewPager mViewPagerPosters;
    private TextView mTextViewPosterCount;
    private PosterSliderAdapter mAdapterPosters;

    @Inject PosterSliderPresenter presenter;

    public static Intent makeIntent(Context context , List<String> posterUrls , String transitionName) {
        Intent intent = new Intent(context , PosterSliderActivity.class);
        intent.putStringArrayListExtra(POSTERS , (ArrayList<String>) posterUrls);
        intent.putExtra(TRANSITION_NAME , transitionName);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewPagerPosters.addOnPageChangeListener(new OnPageScrolled() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                presenter.countPosters(position , mAdapterPosters.getCount());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_posters_slider;
    }

    @Override
    public void showCurrentPosition(String position) {
        mTextViewPosterCount.setText(position);
    }

    @Override
    protected void initViews() {
        mViewPagerPosters = findViewById(R.id.vp_posters);
        mTextViewPosterCount = findViewById(R.id.tv_poster_count);
        mAdapterPosters = new PosterSliderAdapter(getSupportFragmentManager() , getPosterUrls() , false);
        mViewPagerPosters.setAdapter(mAdapterPosters);
        if (isAboveLollipop()){
            mViewPagerPosters.setTransitionName(getIntent().getStringExtra(TRANSITION_NAME));
        }
    }

    @Override
    protected void initDagger() {
        MediatekaApp.getComponentsManager()
                .getAppComponent()
                .inject(this);
    }

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    private List<String> getPosterUrls(){
        return getIntent().getStringArrayListExtra(POSTERS);
    }
}
