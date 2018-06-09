package com.ru.devit.mediateka.presentation.main;

import com.ru.devit.mediateka.UnitTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainPresenterTest extends UnitTest {

    @Mock private MainPresenter.View view;

    private MainPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new MainPresenter();
    }

    @Test
    public void shouldShowErrorWhenInternetNotConnected(){
        presenter.setView(view);
        presenter.initialize();
        presenter.onNetworkConnectionChanged(false);

        verify(view , times(1)).startToListenInternetConnection();
        verify(view , times(1)).showNetworkError();
    }

    @Test
    public void shouldShowErrorIfInternetNotConnectedWhenRetryButtonClicked(){
        presenter.setView(view);
        presenter.onRetryButtonClicked(false);

        verify(view , times(1)).showNetworkError();
    }

    @Test
    public void shouldScrollToFirstPositionWhenFABClicked(){
        presenter.setView(view);
        presenter.onFABScrollUpClicked();

        verify(view , times(1)).scrollToFirstPosition();
    }
}