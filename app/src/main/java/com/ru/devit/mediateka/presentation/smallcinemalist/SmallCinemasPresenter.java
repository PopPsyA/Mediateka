package com.ru.devit.mediateka.presentation.smallcinemalist;

import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaByQuery;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;
import com.ru.devit.mediateka.utils.Constants;

import java.util.Collections;
import java.util.List;

public class SmallCinemasPresenter extends BasePresenter<SmallCinemasPresenter.View> {

    private List<Cinema> cinemas;
    private GetCinemaByQuery getCinemaByQuery;

    public SmallCinemasPresenter(GetCinemaByQuery getCinemaByQuery) {
        this.getCinemaByQuery = getCinemaByQuery;
    }

    @Override
    public void initialize() {
        getView().showLoading();
    }

    @Override
    public void onDestroy() {
        getCinemaByQuery.dispose();
        setView(null);
    }

    public void onGetTextFromSearchField(String query) {
        getView().showLoading();
        getCinemaByQuery.setQuery(query);
        getCinemaByQuery.subscribe(new UseCaseSubscriber<List<Cinema>>() {
            @Override
            public void onNext(List<Cinema> cinemas) {
                getView().showCinemas(cinemas);
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }

    public void setCinemas(List<Cinema> cinemaList){
        cinemas = cinemaList;
        sortByDate(cinemas);
        getView().showCinemas(cinemas);
    }

    public void onCinemaClicked(int cinemaId , int viewHolderPosition){
        getView().openCinema(cinemaId , viewHolderPosition);
    }

    private void sortByDate(List<Cinema> cinemas){
        Collections.sort(cinemas, (cinema, cinema2) -> {
            if (cinema.getReleaseDate().equals(Constants.DEFAULT_VALUE) || cinema2.getReleaseDate().equals(Constants.DEFAULT_VALUE)){
                return cinema.getReleaseDate().compareTo(cinema2.getReleaseDate());
            }
            int releaseDate1 = Integer.valueOf(cinema.getReleaseDate());
            int releaseDate2 = Integer.valueOf(cinema2.getReleaseDate());
            return Integer.compare(releaseDate2 , releaseDate1);
        });
    }

    public interface View extends BaseView{
        void openCinema(int cinemaId , int viewHolderPosition);
        void showCinemas(List<Cinema> cinemas);
    }
}
