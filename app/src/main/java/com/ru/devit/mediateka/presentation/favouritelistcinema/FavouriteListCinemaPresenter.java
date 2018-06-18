package com.ru.devit.mediateka.presentation.favouritelistcinema;

import com.ru.devit.mediateka.domain.cinemausecases.GetFavouriteListCinema;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class FavouriteListCinemaPresenter extends BasePresenter<FavouriteListCinemaPresenter.View> {

    private final GetFavouriteListCinema useCaseFavouriteListCinema;

    public FavouriteListCinemaPresenter(GetFavouriteListCinema useCaseFavouriteListCinema) {
        this.useCaseFavouriteListCinema = useCaseFavouriteListCinema;
    }

    @Override
    public void initialize() {
        getView().showLoading();
        useCaseFavouriteListCinema.subscribe(new DisposableSubscriber<List<Cinema>>() {
            @Override
            public void onNext(List<Cinema> cinemaList) {
                getView().showFavouriteListCinema(cinemaList);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                getView().hideLoading();
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void onDestroy() {
        useCaseFavouriteListCinema.dispose();
    }

    public void onCinemaClicked(int cinemaId) {
        getView().showDetailedCinema(cinemaId);
    }

    interface View extends BaseView {
        void showFavouriteListCinema(List<Cinema> cinemaList);
        void showDetailedCinema(int cinemaId);
    }
}
