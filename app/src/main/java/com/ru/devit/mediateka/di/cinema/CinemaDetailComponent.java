package com.ru.devit.mediateka.di.cinema;

import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailContentFragment;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemasFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CinemaDetailModule.class)
public interface CinemaDetailComponent {

    void inject(CinemaDetailsActivity cinemaDetailsActivity);
    void inject(SmallCinemasFragment smallCinemasFragment);
    void inject(CinemaDetailContentFragment cinemaDetailContentFragment);
}
