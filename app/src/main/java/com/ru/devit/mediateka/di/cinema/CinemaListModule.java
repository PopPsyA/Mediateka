package com.ru.devit.mediateka.di.cinema;

import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetTopRatedCinemas;
import com.ru.devit.mediateka.domain.cinemausecases.GetUpComingCinemas;
import com.ru.devit.mediateka.presentation.cinemalist.CinemaListPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class CinemaListModule {

    @ActivityScope
    @Provides
    CinemaListPresenter provideCinemaListPresenter(GetCinemas getCinemas ,
                                                   GetTopRatedCinemas getTopRatedCinemas ,
                                                   GetUpComingCinemas getUpComingCinemas){
        return new CinemaListPresenter(getCinemas , getTopRatedCinemas , getUpComingCinemas);
    }

    @ActivityScope
    @Provides
    GetCinemas provideGetCinemas(@Named("executor_thread")Scheduler executorThread ,
                                 @Named("ui_thread")Scheduler uiThread ,
                                 CinemaRepository repository){
        return new GetCinemas(executorThread , uiThread , repository);
    }

    @ActivityScope
    @Provides
    GetTopRatedCinemas provideGetTopRatedCinemas(@Named("executor_thread")Scheduler executorThread ,
                                                 @Named("ui_thread")Scheduler uiThread ,
                                                 CinemaRepository repository){
        return new GetTopRatedCinemas(executorThread , uiThread , repository);
    }

    @ActivityScope
    @Provides
    GetUpComingCinemas provideGetUpComingCinemas(@Named("executor_thread")Scheduler executorThread ,
                                                 @Named("ui_thread")Scheduler uiThread ,
                                                 CinemaRepository repository){
        return new GetUpComingCinemas(executorThread , uiThread , repository);
    }
}
