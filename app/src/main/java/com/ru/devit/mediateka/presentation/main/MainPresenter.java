package com.ru.devit.mediateka.presentation.main;

import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import javax.inject.Inject;

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

    public interface View extends BaseView {
        void startToListenInternetConnection();
        void showNetworkError();
        void hideNetworkError();
    }
}
