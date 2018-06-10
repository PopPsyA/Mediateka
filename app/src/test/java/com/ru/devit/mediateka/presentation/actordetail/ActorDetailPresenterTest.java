package com.ru.devit.mediateka.presentation.actordetail;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.domain.actorusecases.GetActorById;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ActorDetailPresenterTest extends UnitTest {

    @Mock private ActorDetailPresenter.View view;
    @Mock private GetActorById useCaseGetActorByIdMock;
    @Mock private List<String> posterUrlsMock;
    @Mock private ActorDetailPresenter.ActorDetailSubscriber subscriberMock;

    private static final int TEST_USER_ID = 123;

    private ActorDetailPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new ActorDetailPresenter(useCaseGetActorByIdMock);
    }

    @Test
    public void shouldSearchActorByIdWhenInitialize(){
        presenter.setView(view);
        presenter.initialize();
        presenter.setActorId(TEST_USER_ID);
        useCaseGetActorByIdMock.searchActorById(TEST_USER_ID);

        verify(view , times(1)).showLoading();
        verify(useCaseGetActorByIdMock).searchActorById(TEST_USER_ID);
    }

    @Test
    public void shouldHideLoadingWhenRuntimeException(){
//        presenter.setView(view);
//        presenter.initialize();
//        doThrow(new RuntimeException()).when(subscriberMock).onError(any(Throwable.class));
//
//        verify(view , times(1)).showLoading();
//        verify(view).hideLoading();
    }

    @Test
    public void shouldShowPostersWhenUserAvatarClicked(){
        presenter.setView(view);
        presenter.onAvatarClicked(posterUrlsMock);

        verify(view , times(1)).showPosters(posterUrlsMock);
    }

    @Test
    public void shouldUseCaseDisposeWhenPresenterDestroy(){
        presenter.onDestroy();

        verify(useCaseGetActorByIdMock , times(1)).dispose();
    }
}