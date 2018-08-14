package com.ru.devit.mediateka.presentation.common;

import com.ru.devit.mediateka.presentation.base.BaseView;

public interface CinemaTabSelectorView extends BaseView {
    void onPopularTabSelected();
    void onTopRatedTabSelected();
    void onUpComingTabSelected();
}
