package com.ru.devit.mediateka.presentation.search;

import com.ru.devit.mediateka.UnitTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchPresenterTest extends UnitTest {

    @Mock private SearchPresenter.View view;

    private SearchPresenter presenter;
    private static final String TEST_QUERY = "test query";
    private static final int CINEMAS_TAB_POSITION = 0;
    private static final int ACTORS_TAB_POSITION = 1;


    @Override
    protected void onSetUp() {
        presenter = new SearchPresenter();
    }

    @Test
    public void shouldSelectCinemaPositionWhenCinemaTabSelected(){
        presenter.setView(view);
        presenter.onTabSelected(CINEMAS_TAB_POSITION);

        verify(view).onCinemaTabSelected();
    }

    @Test
    public void shouldSelectActorPositionWhenActorTabSelected(){
        presenter.setView(view);
        presenter.onTabSelected(ACTORS_TAB_POSITION);

        verify(view).onActorTabSelected();
    }

    @Test
    public void shouldSendQueryToCinemaTabWhenCinemaTabSelected(){
        presenter.setView(view);
        presenter.onTabSelected(CINEMAS_TAB_POSITION);
        presenter.onTextChanged(TEST_QUERY);

        verify(view , times(1)).textFromCinemaTab(TEST_QUERY);
    }

    @Test
    public void shouldSendQueryToActorTabWhenActorTabSelected(){
        presenter.setView(view);
        presenter.onTabSelected(ACTORS_TAB_POSITION);
        presenter.onTextChanged(TEST_QUERY);

        verify(view , times(1)).textFromActorTab(TEST_QUERY);
    }
}