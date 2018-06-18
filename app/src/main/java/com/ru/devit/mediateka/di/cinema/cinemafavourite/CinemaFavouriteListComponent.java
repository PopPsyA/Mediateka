package com.ru.devit.mediateka.di.cinema.cinemafavourite;

import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.presentation.favouritelistcinema.FavouriteListCinemaActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CinemaFavouriteListModule.class)
public interface CinemaFavouriteListComponent {

    void inject(FavouriteListCinemaActivity activity);
}
