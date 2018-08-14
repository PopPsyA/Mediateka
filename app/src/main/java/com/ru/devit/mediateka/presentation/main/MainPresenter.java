package com.ru.devit.mediateka.presentation.main;

import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaTabPositionPicker;
import com.ru.devit.mediateka.presentation.common.CinemaTabSelectorView;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainPresenter.View> implements SyncConnectionListener {

    private final CinemaTabPositionPicker cinemaTabPositionPicker = new CinemaTabPositionPicker();

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
    public void onNetworkConnectionChanged(boolean internetConnected) {
        if (!internetConnected){
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
        cinemaTabPositionPicker.loadCinemaFromCinemaPosition(position , getView());
    }

    public interface View extends CinemaTabSelectorView {
        void startToListenInternetConnection();
        void showNetworkError();
        void hideNetworkError();
        void scrollToFirstPosition();
    }
}
