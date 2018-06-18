package com.ru.devit.mediateka.di.cinema;

import com.ru.devit.mediateka.di.cinema.cinemadetail.CinemaDetailComponent;
import com.ru.devit.mediateka.di.cinema.cinemadetail.CinemaDetailModule;
import com.ru.devit.mediateka.di.cinema.cinemafavourite.CinemaFavouriteListComponent;
import com.ru.devit.mediateka.di.cinema.cinemafavourite.CinemaFavouriteListModule;
import com.ru.devit.mediateka.di.cinema.cinemalist.CinemaListComponent;
import com.ru.devit.mediateka.di.cinema.cinemalist.CinemaListModule;

import dagger.Subcomponent;

@CinemaScope
@Subcomponent(modules = CinemaModule.class)
public interface CinemaComponent {

    CinemaListComponent plusCinemaListComponent(CinemaListModule cinemaListModule);
    CinemaDetailComponent plusCinemaDetailComponent(CinemaDetailModule cinemaDetailModule);
    CinemaFavouriteListComponent plusCinemaFavouriteListComponent(CinemaFavouriteListModule cinemaFavouriteListModule);
}
