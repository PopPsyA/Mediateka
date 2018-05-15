package com.ru.devit.mediateka.presentation.cinemadetail;

import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.base.BasePresenter;
import com.ru.devit.mediateka.presentation.base.BaseView;

public class CinemaDetailContentPresenter extends BasePresenter<CinemaDetailContentPresenter.View> {

    private final int SHORT_DESC_LINES = 5;
    private final int FULL_DESC_LINES = Integer.MAX_VALUE;

    private Cinema cinema;
    private boolean descriptionClicked = false;

    public CinemaDetailContentPresenter(){}

    @Override
    public void initialize() {
        getView().showLoading();
        getView().showShortDescription(SHORT_DESC_LINES);
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
        getView().showCinemaContent(cinema);
        getView().hideLoading();
    }

    void onDescriptionClicked() {
        if (!descriptionClicked) {
            getView().showFullDescription(FULL_DESC_LINES);
            descriptionClicked = true;
        } else {
            getView().showShortDescription(SHORT_DESC_LINES);
            descriptionClicked = false;
        }
    }

    public void onDestroy() {
        setView(null);
    }

    public interface View extends BaseView {
        void showCinemaContent(Cinema cinema);
        void showShortDescription(int lines);
        void showFullDescription(int lines);
    }
}
