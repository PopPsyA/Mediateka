package com.ru.devit.mediateka.presentation.cinemadetail;

import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

public class CinemaDetailContentPresenter extends BasePresenter<CinemaDetailContentPresenter.View> {

    private Cinema cinema;

    public CinemaDetailContentPresenter(){}

    @Override
    public void initialize() {
        getView().showLoading();
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
        getView().showCinemaContent(cinema);
        getView().hideLoading();
    }

    public void onDestroy() {
        setView(null);
    }

    public interface View extends BaseView {
        void showCinemaContent(Cinema cinema);
    }
}
