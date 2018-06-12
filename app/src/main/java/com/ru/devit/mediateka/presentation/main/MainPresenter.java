package com.ru.devit.mediateka.presentation.main;

import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import javax.inject.Inject;

import static com.ru.devit.mediateka.presentation.main.MainActivity.ACTUAL_CINEMAS_TAB_POSITION;
import static com.ru.devit.mediateka.presentation.main.MainActivity.TOP_RATED_CINEMAS_TAB_POSITION;
import static com.ru.devit.mediateka.presentation.main.MainActivity.UP_COMING_CINEMAS_TAB_POSITION;

public class MainPresenter extends BasePresenter<MainPresenter.View> implements SyncConnectionListener {

    @Inject public MainPresenter(){}


    @Override
    public void initialize() {
        getView().startToListenInternetConnection();
    }

    @Override
    public void onDestroy() {
        setView(null);
    }

    @Override
    public void onNetworkConnectionChanged(boolean connected) {
        if (!connected){
            getView().showNetworkError();
        }
    }

    void onRetryButtonClicked(boolean internetConnected) {
        if (!internetConnected){
            getView().showNetworkError();
        } else {
            getView().hideNetworkError();
        }
    }

    public void onFABScrollUpClicked() {
        getView().scrollToFirstPosition();
    }

    public void onTabSelected(int position) {
        switch (position){
            case ACTUAL_CINEMAS_TAB_POSITION: {
                getView().onPopularTabSelected();
                break;
            }
            case TOP_RATED_CINEMAS_TAB_POSITION : {
                getView().onTopRatedTabSelected();
                break;
            }
            case UP_COMING_CINEMAS_TAB_POSITION : {
                getView().onUpComingTabSelected();
                break;
            }
        }
    }

    public interface View extends BaseView {
        void startToListenInternetConnection();
        void showNetworkError();
        void hideNetworkError();
        void onPopularTabSelected();
        void onTopRatedTabSelected();
        void onUpComingTabSelected();
        void scrollToFirstPosition();
    }
}
