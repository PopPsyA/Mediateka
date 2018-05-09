package com.ru.devit.mediateka.di.cinema;

import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailsActivity;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaListFragment;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemasFragment;

import dagger.Subcomponent;

@CinemaScope
@Subcomponent(modules = CinemaModule.class)
public interface CinemaComponent {

    CinemaListComponent plusCinemaListComponent(CinemaListModule cinemaListModule);
    CinemaDetailComponent plusCinemaDetailComponent(CinemaDetailModule cinemaDetailModule);
}
