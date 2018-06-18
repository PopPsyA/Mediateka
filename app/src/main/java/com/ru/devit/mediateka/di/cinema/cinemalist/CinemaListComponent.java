package com.ru.devit.mediateka.di.cinema.cinemalist;

import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaListFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CinemaListModule.class)
public interface CinemaListComponent {

    void inject(CinemaListFragment cinemaListFragment);
}
