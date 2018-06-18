package com.ru.devit.mediateka.di.cinema.cinemafavourite;

import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.cinemausecases.GetFavouriteListCinema;
import com.ru.devit.mediateka.presentation.favouritelistcinema.FavouriteListCinemaPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class CinemaFavouriteListModule {

    @ActivityScope
    @Provides
    FavouriteListCinemaPresenter provideFavouriteListCinemaPresenter(GetFavouriteListCinema useCase){
        return new FavouriteListCinemaPresenter(useCase);
    }

    @ActivityScope
    @Provides
    GetFavouriteListCinema provideGetFavouriteListCinema(@Named("executor_thread")Scheduler executorThread ,
                                                         @Named("ui_thread")Scheduler uiThread ,
                                                         CinemaLocalRepository repository){
        return new GetFavouriteListCinema(executorThread , uiThread , repository);
    }
}
