package com.ru.devit.mediateka.presentation.favouritelistcinema;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.ru.devit.mediateka.data.SharedPreferenceManager;
import com.ru.devit.mediateka.domain.cinemausecases.GetFavouriteListCinema;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class FavouriteListCinemaPresenter extends BasePresenter<FavouriteListCinemaPresenter.View> {

    private List<Cinema> cinemaList;
    private final GetFavouriteListCinema useCaseFavouriteListCinema;
    private final SharedPreferenceManager sharedPreferenceManager;

    public FavouriteListCinemaPresenter(GetFavouriteListCinema useCaseFavouriteListCinema ,
                                        SharedPreferenceManager sharedPreferenceManager) {
        this.useCaseFavouriteListCinema = useCaseFavouriteListCinema;
        this.sharedPreferenceManager = sharedPreferenceManager;
        this.cinemaList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        getView().showLoading();
        getView().showSavedSortedPosition(sharedPreferenceManager.getCinemaSortingPosition());
        useCaseFavouriteListCinema.subscribe(new DisposableSubscriber<List<Cinema>>() {
            @Override
            public void onNext(List<Cinema> cinemas) {
                cinemaList = cinemas;
                removeNotNecessaryInfoFromCinemaList(cinemaList);
                getView().showFavouriteListCinema(cinemas);
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

    public void onCinemaClicked(int cinemaId , int viewHolderPos) {
        getView().showDetailedCinema(cinemaId , viewHolderPos);
    }

    @SuppressLint("CheckResult")
    public void onCinemaSwiped(int position) {
        String cinemaTitle = cinemaList.get(position).getTitle();
        final Cinema deletedCinema = cinemaList.get(position);
        cinemaList.remove(position);
        useCaseFavouriteListCinema
                .removeFavouriteCinema(deletedCinema.getId())
                .subscribe(() -> getView().showUndoAction(cinemaTitle , deletedCinema , position));
    }

    @SuppressLint("CheckResult")
    public void onMenuClearFavouriteListClicked() {
        cinemaList.clear();
        useCaseFavouriteListCinema
                .clearFavouriteList()
                .subscribe(getView()::showSuccessfullyFavouriteListCleared);
    }

    public void onUndoClicked(Cinema deletedCinema, int deletedIndex) {
        useCaseFavouriteListCinema
                .saveFavouriteCinema(deletedCinema.getId())
                .subscribe();
        cinemaList.add(deletedIndex , deletedCinema);
    }

    public void setCinemaList(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    public void onCinemaSortingDialogItemClicked(int position) {
        Collections.sort(cinemaList , useCaseFavouriteListCinema.createCinemaListComparator(position));
        sharedPreferenceManager.saveCinemaSortingPosition(position);
        getView().showFavouriteListCinema(cinemaList);
    }

    private void removeNotNecessaryInfoFromCinemaList(List<Cinema> cinemas){
        for (Cinema cinema : cinemas){
            if (!TextUtils.isEmpty(cinema.getCharacter())){
                cinema.setCharacter("");
            }
        }
    }

    @Override
    public void onDestroy() {
        setView(null);
        useCaseFavouriteListCinema.dispose();
    }

    interface View extends BaseView {
        void showFavouriteListCinema(List<Cinema> cinemaList);
        void showDetailedCinema(int cinemaId , int viewHolderPos);
        void showUndoAction(String cinemaTitle , Cinema deletedCinema , int deletedIndex);
        void showSuccessfullyFavouriteListCleared();
        void showSavedSortedPosition(int savedPosition);
    }
}
