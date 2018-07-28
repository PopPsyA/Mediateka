package com.ru.devit.mediateka.presentation.smallcinemalist;

import com.ru.devit.mediateka.domain.Actions;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaByQuery;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;


public class SmallCinemasPresenter extends BasePresenter<SmallCinemasPresenter.View> {

    private List<Cinema> cinemas;
    private final GetCinemaByQuery useCaseGetCinemaByQuery;
    private final SmallCinemaSubscriber smallCinemaSubscriber;

    public SmallCinemasPresenter(GetCinemaByQuery useCaseGetCinemaByQuery) {
        this.useCaseGetCinemaByQuery = useCaseGetCinemaByQuery;
        smallCinemaSubscriber = new SmallCinemaSubscriber();
    }

    @Override
    public void initialize() {
        getView().showLoading();
        useCaseGetCinemaByQuery.subscribe(smallCinemaSubscriber);
        useCaseGetCinemaByQuery.setActions(new Actions(
                () -> getView().showLoading() ,
                () -> getView().hideLoading() ,
                () -> getView().clearAdapter()
        ));
        getView().hideLoading();
    }

    @Override
    public void onDestroy() {
        useCaseGetCinemaByQuery.removeActions();
        useCaseGetCinemaByQuery.dispose();
        setView(null);
    }

    void onGetTextFromSearchField(String query) {
        useCaseGetCinemaByQuery.onNextQuery(query);
    }

    public void setCinemas(List<Cinema> cinemaList){
        cinemas = cinemaList;
        useCaseGetCinemaByQuery.sortByDate(cinemas);
        getView().showCinemas(cinemas);
        getView().hideLoading();
    }

    public void onCinemaClicked(int cinemaId , int viewHolderPosition){
        getView().openCinema(cinemaId , viewHolderPosition);
    }

    private class SmallCinemaSubscriber extends UseCaseSubscriber<List<Cinema>>{
        @Override
        public void onNext(List<Cinema> cinemas) {
            getView().showCinemas(cinemas);
            getView().hideLoading();
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
        }
    }

    public interface View extends BaseView{
        void openCinema(int cinemaId , int viewHolderPosition);
        void showCinemas(List<Cinema> cinemas);
        void clearAdapter();
    }
}
