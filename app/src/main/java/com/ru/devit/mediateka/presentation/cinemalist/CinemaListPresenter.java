package com.ru.devit.mediateka.presentation.cinemalist;


import android.util.Log;

import com.ru.devit.mediateka.domain.cinemausecases.GetCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetTopRatedCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetUpComingCinemas;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.common.CinemaTabSelectorView;

import java.util.List;

public class CinemaListPresenter extends BasePresenter<CinemaListPresenter.View> {

    private int currentPage = 1;
    private int totalPage = 0;
    private String tabPositionName = "POPULAR";
    private boolean isCinemaInRowBig = true;

    private final CinemaTabPositionPicker cinemaTabPositionPicker;

    public CinemaListPresenter(GetCinemas getCinemas ,
                               GetTopRatedCinemas getTopRatedCinemas ,
                               GetUpComingCinemas getUpComingCinemas) {
        cinemaTabPositionPicker = new CinemaTabPositionPicker(
                getCinemas ,
                getTopRatedCinemas ,
                getUpComingCinemas);
    }

    @Override
    public void initialize() {
        getView().showLoading();
    }

    public void loadCinemas(){
        cinemaTabPositionPicker.loadCinemaFromCinemaTabName(tabPositionName , new CinemaListSubscriber() , getView());
    }

    public void setTabPositionName(String tabPositionName) {
        this.tabPositionName = tabPositionName;
    }

    public boolean isLastPage(){
        return currentPage == totalPage;
    }

    public void onCinemaClicked(int cinemaId , int viewHolderPosition){
        getView().openCinemaDetails(cinemaId , viewHolderPosition);
    }

    public void onLoadNextPage(){
        currentPage += 1;
        setCurrentPage(currentPage);
        loadCinemas();
    }

    private void setCurrentPage(int currentPage){
        cinemaTabPositionPicker.setCurrentPage(currentPage);
    }

    public void onDestroy(){
        setView(null);
    }

    public void onMenuItemHowToShowCinemaListClicked() {
        if (isCinemaInRowBig){
            cinemaTabPositionPicker.loadCinemaFromCinemaTabName(tabPositionName , new CinemaListSubscriber() , getView());
            getView().showSmallCinemaListInOneRow();
            isCinemaInRowBig = false;
        } else {
            cinemaTabPositionPicker.loadCinemaFromCinemaTabName(tabPositionName , new CinemaListSubscriber() , getView());
            getView().showBigCinemaListInOneRow();
            isCinemaInRowBig = true;
        }
    }

    public interface View extends CinemaTabSelectorView {
        void showCinemas(List<Cinema> cinemaEntities);
        void openCinemaDetails(int cinemaId , int viewHolderPosition);
        void showSmallCinemaListInOneRow();
        void showBigCinemaListInOneRow();
    }

    private class CinemaListSubscriber extends UseCaseSubscriber<List<Cinema>> {
        @Override
        public void onNext(List<Cinema> cinemas) {
            if (cinemas.size() == 0){
                return;
            }
            Log.d("lolkek" , "cinemas " + cinemas);
            totalPage = cinemas.get(0).getTotalPages();
            getView().showCinemas(cinemas);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            getView().hideLoading();
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
