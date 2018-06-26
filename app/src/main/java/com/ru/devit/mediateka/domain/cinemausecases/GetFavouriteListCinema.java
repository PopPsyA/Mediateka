package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetFavouriteListCinema extends UseCase<List<Cinema>> {

    private final CinemaLocalRepository repository;
    private final Scheduler ioThread;
    private final Scheduler uiThread;

    @Inject
    public GetFavouriteListCinema(@Named("executor_thread") Scheduler executorThread ,
                                  @Named("ui_thread") Scheduler uiThread ,
                                  CinemaLocalRepository repository) {
        super(executorThread, uiThread);
        this.ioThread = executorThread;
        this.uiThread = uiThread;
        this.repository = repository;
    }

    @Override
    protected Flowable<List<Cinema>> createUseCase() {
        return repository.getFavouriteListCinema()
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .sorted((o1, o2) -> o2.getReleaseDate().compareTo(o1.getReleaseDate()))
                .toList()
                .toFlowable();
    }

    public Completable saveFavouriteCinema(final int cinemaId){
        return repository.saveIntoDatabaseFavouriteCinema(cinemaId)
                .subscribeOn(ioThread)
                .observeOn(uiThread);
    }

    public Completable removeFavouriteCinema(final int cinemaId){
        return repository.removeFromDatabaseFavouriteCinema(cinemaId)
                .subscribeOn(ioThread)
                .observeOn(uiThread);
    }

    public Completable clearFavouriteList() {
        return repository.clearFavouriteListCinema()
                .subscribeOn(ioThread)
                .observeOn(uiThread);
    }
}
