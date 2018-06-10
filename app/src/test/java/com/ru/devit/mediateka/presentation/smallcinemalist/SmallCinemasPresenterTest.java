package com.ru.devit.mediateka.presentation.smallcinemalist;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaByQuery;
import com.ru.devit.mediateka.models.model.Cinema;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SmallCinemasPresenterTest extends UnitTest {

    @Mock private SmallCinemasPresenter.View view;
    @Mock private GetCinemaByQuery useCaseGetCinemaByQueryMock;
    @Mock private List<Cinema> cinemaListMock;

    private static final int TEST_CINEMA_ID = 987;

    private SmallCinemasPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new SmallCinemasPresenter(useCaseGetCinemaByQueryMock);
    }

    @Test
    public void shouldOpenCinemaDetailScreenWhenCinemaClicked(){
        presenter.setView(view);
        presenter.onCinemaClicked(TEST_CINEMA_ID , 6);

        verify(view).openCinema(TEST_CINEMA_ID , 6);
    }

    @Test
    public void shouldShowLoadingWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();

        verify(view , times(1)).showLoading();
        verify(view , times(1)).hideLoading();
    }

    @Test
    public void shouldSetQueryFromSearchFieldWhenCome(){
        presenter.onGetTextFromSearchField("Predator");

        verify(useCaseGetCinemaByQueryMock).onNextQuery("Predator");
    }

    @Test
    public void shouldSortCinemasByDateWhenSetCinemas(){
        presenter.setView(view);
        presenter.setCinemas(cinemaListMock);

        verify(view).showCinemas(cinemaListMock);
        verify(view).hideLoading();
    }

    @Test
    public void shouldUseCaseDisposeWhenPresenterDestroy(){
        presenter.onDestroy();

        verify(useCaseGetCinemaByQueryMock).removeActions();
        verify(useCaseGetCinemaByQueryMock).dispose();
    }
}