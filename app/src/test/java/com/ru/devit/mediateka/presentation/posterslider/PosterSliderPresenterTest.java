package com.ru.devit.mediateka.presentation.posterslider;

import com.ru.devit.mediateka.UnitTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PosterSliderPresenterTest extends UnitTest {

    @Mock private PosterSliderPresenter.View view;

    private PosterSliderPresenter presenter;

    @Override
    protected void onSetUp() {
        presenter = new PosterSliderPresenter();
    }

    @Test
    public void shouldChangeCurrentPositionWhenPosterSlide(){
        presenter.setView(view);
        presenter.countPosters(2 , 10);

        verify(view , times(1)).showCurrentPosition("3/10"); // 3/10 because position start from 0 i.e currentPos += 1 :)
    }
}