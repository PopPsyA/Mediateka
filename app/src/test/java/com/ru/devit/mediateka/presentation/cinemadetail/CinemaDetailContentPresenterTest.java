package com.ru.devit.mediateka.presentation.cinemadetail;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailContentPresenter;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CinemaDetailContentPresenterTest extends UnitTest {

    @Mock private CinemaDetailContentPresenter.View view;

    private CinemaDetailContentPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new CinemaDetailContentPresenter();
    }

    @Test
    public void shouldShowLoadingWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();

        verify(view , times(1)).showLoading();
    }

    @Test
    public void shouldSetCinema(){
        presenter.setView(view);
        presenter.setCinema(any(Cinema.class));

        verify(view).showCinemaContent(any(Cinema.class));
        verify(view).hideLoading();
    }
}