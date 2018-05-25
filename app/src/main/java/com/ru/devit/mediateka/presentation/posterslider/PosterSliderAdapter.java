package com.ru.devit.mediateka.presentation.posterslider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Collections;
import java.util.List;

public class PosterSliderAdapter extends FragmentStatePagerAdapter {

    private List<String> posterUrls;
    private final boolean isForBackgroundPoster;

    public PosterSliderAdapter(FragmentManager fm , List<String> posterUrls , boolean isForBackgroundPoster) {
        super(fm);
        this.posterUrls = posterUrls;
        this.isForBackgroundPoster = isForBackgroundPoster;
        checkNotNull(posterUrls);
    }

    @Override
    public Fragment getItem(int position) {
        return PosterSliderFragment.newInstance(posterUrls.get(position) , isForBackgroundPoster);
    }

    @Override
    public int getCount() {
        return posterUrls.size();
    }

    private void checkNotNull(List<String> urls){
        if (urls == null){
            posterUrls = Collections.emptyList();
        }
    }
}
