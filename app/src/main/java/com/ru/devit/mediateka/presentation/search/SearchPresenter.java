package com.ru.devit.mediateka.presentation.search;

import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import javax.inject.Inject;


public class SearchPresenter extends BasePresenter<SearchPresenter.View> {

    private static int currentTabPosition = 0;

    private static final int CINEMAS_TAB_POSITION = 0;
    private static final int ACTORS_TAB_POSITION = 1;

    @Inject public SearchPresenter() {
    }

    @Override
    public void initialize() {
        getView().showLoading();
    }

    @Override
    public void onDestroy() {
    }

    public void onTabSelected(int position) {
        switch (position) {
            case CINEMAS_TAB_POSITION : {
                getView().onCinemaTabSelected();
                currentTabPosition = CINEMAS_TAB_POSITION;
                break;
            }
            case ACTORS_TAB_POSITION : {
                getView().onActorTabSelected();
                currentTabPosition = ACTORS_TAB_POSITION;
                break;
            }
        }
    }

    public void onTextChanged(String query) {
        switch (currentTabPosition) {
            case CINEMAS_TAB_POSITION : {
                getView().textFromCinemaTab(query);
                break;
            }
            case ACTORS_TAB_POSITION : {
                getView().textFromActorTab(query);
                break;
            }
        }
    }


    public interface View extends BaseView{
        void onCinemaTabSelected();
        void onActorTabSelected();
        void textFromCinemaTab(String query);
        void textFromActorTab(String query);
    }
}
