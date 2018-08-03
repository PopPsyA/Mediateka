package com.ru.devit.mediateka.presentation.cinemadetail;

import android.annotation.SuppressLint;

import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaById;
import com.ru.devit.mediateka.domain.UseCaseSubscriber;
import com.ru.devit.mediateka.domain.cinemausecases.GetFavouriteListCinema;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.model.DateAndTimeInfo;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

import java.util.List;

public class CinemaDetailPresenter extends BasePresenter<CinemaDetailPresenter.View> {

    private int cinemaId;
    private boolean isFABMenuOpen;
    private Cinema cinemaInPresenter;

    private final GetCinemaById useCaseGetCinemaById;
    private final GetFavouriteListCinema useCaseGetFavouriteListCinema;

    public CinemaDetailPresenter(GetCinemaById useCaseGetCinemaById ,
                                 GetFavouriteListCinema useCaseGetFavouriteListCinema) {
        this.useCaseGetCinemaById = useCaseGetCinemaById;
        this.useCaseGetFavouriteListCinema = useCaseGetFavouriteListCinema;
    }

    @Override
    public void initialize() {
        getView().showLoading();
        useCaseGetCinemaById.searchCinemaById(cinemaId);
        useCaseGetCinemaById.subscribe(new CinemaDetailSubscriber());
    }

    public void setCinemaId(int cinemaId){
        this.cinemaId = cinemaId;
    }

    public void onSmallPosterClicked(List<String> posterUrls) {
        getView().showListPosters(posterUrls);
    }

    public void onFABCinemaMenuClicked() {
        if (!isFABMenuOpen){
            isFABMenuOpen = true;
            getView().showFABCinemaMenu();
        } else {
            hideFABMenu();
        }
    }

    public void onForegroundViewClicked() {
        hideFABMenu();
    }

    @SuppressLint("CheckResult")
    public void onAddFavouriteCinemaClicked() {
        hideFABMenu();
        useCaseGetFavouriteListCinema.saveFavouriteCinema(cinemaId)
                .subscribe(getView()::showSuccessfullyFavouriteCinemaAdded);
    }

    public void onShowedDateAndTimePickerDialog(DateAndTimeInfo dateAndTimeInfo) {
        if (useCaseGetCinemaById.isRetrievedTimeMoreThanCurrentTime(dateAndTimeInfo)){
            getView().showSuccessfullyCinemaScheduled();
            getView().sendScheduledCinemaNotification(cinemaId , cinemaInPresenter.getTitle() , cinemaInPresenter.getDescription() , dateAndTimeInfo);
        } else {
            getView().showMessageThatUserSelectedIncorrectTime();
        }
    }

    public void onDestroy(){
        useCaseGetCinemaById.dispose();
        useCaseGetFavouriteListCinema.dispose();
        setView(null);
    }

    private void hideFABMenu() {
        isFABMenuOpen = false;
        getView().hideFABCinemaMenu();
    }

    public interface View extends BaseView{
        void showCinemaDetail(Cinema cinemaDetail);
        void showListPosters(List<String> postersUrl);
        void showFABCinemaMenu();
        void hideFABCinemaMenu();
        void showSuccessfullyFavouriteCinemaAdded();
        void sendScheduledCinemaNotification(int cinemaId , String title, String description, DateAndTimeInfo dateAndTimeInfo);
        void showSuccessfullyCinemaScheduled();
        void showMessageThatUserSelectedIncorrectTime();
    }

    private final class CinemaDetailSubscriber extends UseCaseSubscriber<Cinema>{

        @Override
        public void onNext(Cinema cinema) {
            cinemaInPresenter = cinema;
            getView().showCinemaDetail(cinema);
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
