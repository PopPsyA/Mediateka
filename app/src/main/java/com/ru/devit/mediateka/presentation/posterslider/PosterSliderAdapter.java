package com.ru.devit.mediateka.presentation.posterslider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class PosterSliderAdapter extends FragmentStatePagerAdapter {

    private List<String> posterUrls;

    PosterSliderAdapter(FragmentManager fm , List<String> posterUrls) {
        super(fm);
        this.posterUrls = posterUrls;
    }

    @Override
    public Fragment getItem(int position) {
        return PosterSliderFragment.newInstance(posterUrls.get(position));
    }

    @Override
    public int getCount() {
        return posterUrls.size();
    }
}
