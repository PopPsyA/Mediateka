package com.ru.devit.mediateka.presentation.smallcinemalist;

import com.ru.devit.mediateka.domain.Actions;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaByQuery;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;
import com.ru.devit.mediateka.utils.FormatterUtils;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;

import java.util.Collections;
import java.util.List;

import static com.ru.devit.mediateka.utils.FormatterUtils.*;


public class SmallCinemasPresenter extends BasePresenter<SmallCinemasPresenter.View> {

    private List<Cinema> cinemas;
    private GetCinemaByQuery getCinemaByQuery;
    private final SmallCinemaSubscriber smallCinemaSubscriber;

    public SmallCinemasPresenter(GetCinemaByQuery getCinemaByQuery) {
        this.getCinemaByQuery = getCinemaByQuery;
        smallCinemaSubscriber = new SmallCinemaSubscriber();
    }

    @Override
    public void initialize() {
        getView().showLoading();
        getCinemaByQuery.subscribe(smallCinemaSubscriber);
        getCinemaByQuery.setActions(new Actions(
                () -> getView().showLoading() ,
                () -> getView().hideLoading() ,
                () -> getView().clearAdapter()
        ));
        getView().hideLoading();
    }

    @Override
    public void onDestroy() {
        getCinemaByQuery.removeActions();
        getCinemaByQuery.dispose();
        setView(null);
    }

    void onGetTextFromSearchField(String query) {
        getCinemaByQuery.onNextQuery(query);
    }

    public void setCinemas(List<Cinema> cinemaList){
        cinemas = cinemaList;
        sortByDate(cinemas);
        getView().showCinemas(cinemas);
        getView().hideLoading();
    }

    public void onCinemaClicked(int cinemaId , int viewHolderPosition){
        getView().openCinema(cinemaId , viewHolderPosition);
    }

    private void sortByDate(List<Cinema> cinemas){
        Collections.sort(cinemas, (cinema, cinema2) -> {
            if (cinema.getReleaseDate().equals(FormatterUtils.DEFAULT_VALUE) || cinema2.getReleaseDate().equals(DEFAULT_VALUE)){
                return cinema.getReleaseDate().compareTo(cinema2.getReleaseDate());
            }
            int releaseDate1 = Integer.valueOf(cinema.getReleaseDate());
            int releaseDate2 = Integer.valueOf(cinema2.getReleaseDate());
            return Integer.compare(releaseDate2 , releaseDate1);
        });
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
