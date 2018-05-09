package com.ru.devit.mediateka.di.cinema;

import com.ru.devit.mediateka.data.repository.cinema.CinemaRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaById;
import com.ru.devit.mediateka.domain.cinemausecases.GetCinemaByQuery;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailContentPresenter;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailPresenter;
import com.ru.devit.mediateka.presentation.smallcinemalist.SmallCinemasPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class CinemaDetailModule {

    @ActivityScope
    @Provides
    CinemaDetailPresenter provideCinemaDetailPresenter(GetCinemaById getCinemaById){
        return new CinemaDetailPresenter(getCinemaById);
    }

    @ActivityScope
    @Provides
    SmallCinemasPresenter provideSmallCinemasPresenter(GetCinemaByQuery getCinemaByQuery){
        return new SmallCinemasPresenter(getCinemaByQuery);
    }

    @ActivityScope
    @Provides
    CinemaDetailContentPresenter provideCinemaDetailContentPresenter(){
        return new CinemaDetailContentPresenter();
    }

    @ActivityScope
    @Provides
    GetCinemaById providesGetCinemaById(@Named("executor_thread") Scheduler executorThread ,
                                        @Named("ui_thread")Scheduler uiThread ,
                                        CinemaRepository repository){
        return new GetCinemaById(executorThread , uiThread , repository);
    }

    @ActivityScope
    @Provides
    GetCinemaByQuery providesGetCinemaByQuery(@Named("executor_thread") Scheduler executorThread ,
                                        @Named("ui_thread")Scheduler uiThread ,
                                        CinemaRepository repository){
        return new GetCinemaByQuery(executorThread , uiThread , repository);
    }
}
