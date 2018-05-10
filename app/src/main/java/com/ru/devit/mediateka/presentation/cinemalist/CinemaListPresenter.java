package com.ru.devit.mediateka.presentation.cinemalist;

import android.util.Log;

import com.ru.devit.mediateka.domain.cinemausecases.GetCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetTopRatedCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetUpComingCinemas;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

import static com.ru.devit.mediateka.presentation.main.MainActivity.ACTUAL_CINEMAS_TAB_POSITION;
import static com.ru.devit.mediateka.presentation.main.MainActivity.TOP_RATED_CINEMAS_TAB_POSITION;
import static com.ru.devit.mediateka.presentation.main.MainActivity.UP_COMING_CINEMAS_TAB_POSITION;

public class CinemaListPresenter extends BasePresenter<CinemaListPresenter.View> {

    private int currentPage = 1;
    private int totalPage = 0;
    private int tabPosition = 0;

    private GetCinemas getCinemas;
    private GetTopRatedCinemas getTopRatedCinemas;
    private GetUpComingCinemas getUpComingCinemas;

    public CinemaListPresenter(GetCinemas getCinemas, GetTopRatedCinemas getTopRatedCinemas, GetUpComingCinemas getUpComingCinemas) {
        this.getCinemas = getCinemas;
        this.getTopRatedCinemas = getTopRatedCinemas;
        this.getUpComingCinemas = getUpComingCinemas;
    }

    @Override
    public void initialize() {
        getView().showLoading();
    }

    public void loadCinemas(){
        getView().showLoading();
        switch (tabPosition){
            case ACTUAL_CINEMAS_TAB_POSITION: {
                getView().onPopularTabSelected();
                final CinemaListSubscriber subscriber = new CinemaListSubscriber();
                getCinemas.subscribe(subscriber);
                break;
            }
            case TOP_RATED_CINEMAS_TAB_POSITION : {
                getView().onTopRatedTabSelected();
                final CinemaListSubscriber subscriber = new CinemaListSubscriber();
                getTopRatedCinemas.subscribe(subscriber);
                break;
            }
            case UP_COMING_CINEMAS_TAB_POSITION : {
                getView().onUpComingTabSelected();
                final CinemaListSubscriber subscriber = new CinemaListSubscriber();
                getUpComingCinemas.subscribe(subscriber);
                break;
            }
        }
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public boolean isLastPage(){
        return currentPage == totalPage;
    }

    public void onCinemaClicked(int cinemaId , int viewHolderPosition){
        getView().openCinemaDetails(cinemaId , viewHolderPosition);
    }

    public void onLoadNextPage(){
        currentPage = currentPage + 1;
        setCurrentPage(currentPage);
        loadCinemas();
    }

    private void setCurrentPage(int currentPage){
        getCinemas.setCurrentPage(currentPage);
        getTopRatedCinemas.setCurrentPage(currentPage);
        getUpComingCinemas.setCurrentPage(currentPage);
    }

    public void onDestroy(){
        getCinemas.dispose();
        getTopRatedCinemas.dispose();
        getUpComingCinemas.dispose();
        setView(null);
    }

    public interface View extends BaseView{
        void showCinemas(List<Cinema> cinemaEntities);
        void showError(String message);
        void openCinemaDetails(int cinemaId , int viewHolderPosition);
        void onPopularTabSelected();
        void onTopRatedTabSelected();
        void onUpComingTabSelected();
    }

    private class CinemaListSubscriber extends UseCaseSubscriber<List<Cinema>> {
        @Override
        public void onNext(List<Cinema> cinemas) {
//            if (cinemas.size() == 0){
//                getView().showNetworkError("Чтобы синхронизировать данные , подкюлчитесь к интернету");
//                return;
//            }
            if (cinemas.size() == 0){
                return;
            }
            totalPage = cinemas.get(0).getTotalPages();
            getView().showCinemas(cinemas);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            getView().hideLoading();
            getView().showError("Чтобы синхронизировать данные , подкюлчитесь к интернету");
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
