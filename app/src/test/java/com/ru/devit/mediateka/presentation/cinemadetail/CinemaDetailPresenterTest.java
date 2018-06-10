package com.ru.devit.mediateka.presentation.cinemadetail;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaById;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class CinemaDetailPresenterTest extends UnitTest {

    @Mock private CinemaDetailPresenter.View view;
    @Mock private GetCinemaById useCaseGetCinemaByIdMock;
    @Mock private List<String> posterUrlsMock;

    private static final int TEST_CINEMA_ID = 456;

    private CinemaDetailPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new CinemaDetailPresenter(useCaseGetCinemaByIdMock);
    }

    @Test
    public void shouldSearchCinemaByIdWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();
        presenter.setCinemaId(TEST_CINEMA_ID);
        useCaseGetCinemaByIdMock.searchCinemaById(TEST_CINEMA_ID);

        verify(view).showLoading();
        verify(useCaseGetCinemaByIdMock).searchCinemaById(TEST_CINEMA_ID);
    }

    @Test
    public void shouldShowPostersWhenPosterClicked(){
        presenter.setView(view);
        presenter.onSmallPosterClicked(posterUrlsMock);

        verify(view).showListPosters(posterUrlsMock);
    }

    @Test
    public void shouldUseCaseDisposeWhenPresenterDestroy(){
        presenter.onDestroy();

        verify(useCaseGetCinemaByIdMock).dispose();
    }
}