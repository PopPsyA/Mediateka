package com.ru.devit.mediateka.presentation.posterslider;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.presentation.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PosterSliderActivity extends BaseActivity {

    private static final String POSTERS = "cinema_posters";

    private ViewPager mViewPagerPosters;
    private PosterSliderAdapter mAdapterPosters;

    public static Intent makeIntent(Context context , List<String> posterUrls) {
        Intent intent = new Intent(context , PosterSliderActivity.class);
        intent.putStringArrayListExtra(POSTERS , (ArrayList<String>) posterUrls);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_posters_slider;
    }

    @Override
    protected void initViews() {
        mViewPagerPosters = findViewById(R.id.vp_posters);
        List<String> posters = getIntent().getStringArrayListExtra(POSTERS);
        mAdapterPosters = new PosterSliderAdapter(getSupportFragmentManager() , posters);
        mViewPagerPosters.setAdapter(mAdapterPosters);
    }

    @Override
    protected void initPresenter() {

    }
}
