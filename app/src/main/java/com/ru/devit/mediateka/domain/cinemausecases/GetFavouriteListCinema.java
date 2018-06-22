package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GetFavouriteListCinema extends UseCase<List<Cinema>> {

    private final CinemaLocalRepository repository;

    @Inject
    public GetFavouriteListCinema(@Named("executor_thread") Scheduler executorThread ,
                                  @Named("ui_thread") Scheduler uiThread ,
                                  CinemaLocalRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<Cinema>> createUseCase() {
        return repository.getFavouriteListCinema()
                .toFlowable();
    }

    public Completable saveFavouriteCinema(final int cinemaId){
        return repository.saveIntoDatabaseFavouriteCinema(cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable removeFavouriteCinema(final int cinemaId){
        return repository.removeFromDatabaseFavouriteCinema(cinemaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearFavouriteList() {
        return repository.clearFavouriteListCinema()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
