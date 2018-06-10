package com.ru.devit.mediateka.presentation.actorlist;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.domain.actorusecases.GetActorsByQuery;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ActorsPresenterTest extends UnitTest {

    @Mock private ActorsPresenter.View view;
    @Mock private GetActorsByQuery useCaseGetActorsByQueryMock;

    private ActorsPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new ActorsPresenter(useCaseGetActorsByQueryMock);
    }

    @Test
    public void shouldOpenActorDetailedScreenWhenActorClicked(){
        presenter.setView(view);
        presenter.onActorClicked(17890 , 3);

        verify(view , times(1)).openActor(17890 , 3);
    }

    @Test
    public void shouldUseCaseDisposeWhenPresenterDestroy(){
        presenter.onDestroy();

        verify(useCaseGetActorsByQueryMock).removeActions();
        verify(useCaseGetActorsByQueryMock).dispose();
    }

    @Test
    public void shouldShowLoadingWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();

        InOrder inOrder = inOrder(view);

        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
    }

    @Test
    public void shouldSetQueryWhenPresenterSetQuery(){
        presenter.onGetTextFromSearchField("Transformers");

        verify(useCaseGetActorsByQueryMock).setQuery("Transformers");
    }
}