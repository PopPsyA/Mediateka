package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetFavouriteListCinema extends UseCase<List<Cinema>> {

    private final CinemaLocalRepository repository;
    private final Map<Integer , Comparator<Cinema>> mapOfCinemaComparators = new HashMap<Integer, Comparator<Cinema>>(){
        {
            put(SORT_BY_CINEMA_DATE, (o1, o2) -> o2.getReleaseDate().compareTo(o1.getReleaseDate()));
            put(SORT_BY_CINEMA_TITLE , (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
            put(SORT_BY_CINEMA_GENRE , (o1, o2) -> o2.getGenres().length - o1.getGenres().length);
        }
    };

    private static final int SORT_BY_CINEMA_DATE = 0;
    private static final int SORT_BY_CINEMA_TITLE = 1;
    private static final int SORT_BY_CINEMA_GENRE = 2;


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
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .sorted((o1, o2) -> o2.getReleaseDate().compareTo(o1.getReleaseDate()))
                .toList()
                .toFlowable();
    }

    public Completable saveFavouriteCinema(final int cinemaId){
        return repository.saveIntoDatabaseFavouriteCinema(cinemaId)
                .compose(applyCompletableSchedulers());
    }

    public Completable removeFavouriteCinema(final int cinemaId){
        return repository.removeFromDatabaseFavouriteCinema(cinemaId)
                .compose(applyCompletableSchedulers());
    }

    public Completable clearFavouriteList() {
        return repository.clearFavouriteListCinema()
                .compose(applyCompletableSchedulers());
    }

    public Comparator<Cinema> createCinemaListComparator(int position) {
        return mapOfCinemaComparators.get(position);
    }
}
