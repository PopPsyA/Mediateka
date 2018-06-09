package com.ru.devit.mediateka.presentation.actordetail;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.model.Actor;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ActorDetailContentPresenterTest extends UnitTest {

    @Mock private ActorDetailContentPresenter.View view;

    private ActorDetailContentPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new ActorDetailContentPresenter();
    }

    @Test
    public void shouldShowLoadingWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();

        verify(view , times(1)).showLoading();
    }

    @Test
    public void shouldSetActor(){
        presenter.setView(view);
        presenter.setActor(any(Actor.class));

        verify(view , times(1)).showActorInfo(any(Actor.class));
    }
}