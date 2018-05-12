package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.Constants;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetTopRatedCinemas extends UseCase<List<Cinema>> {

    private final CinemaRepository repository;

    public GetTopRatedCinemas(Scheduler executorThread ,
                              Scheduler uiThread ,
                              CinemaRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    public Flowable<List<Cinema>> createUseCase() {
        return repository.getTopRatedCinemas(pageIndex)
                .toFlowable()
                .flatMap(cinemas -> Flowable.fromIterable(cinemas)
                .filter(cinema -> !cinema.getDescription().equals(""))
                .filter(cinema -> !cinema.getReleaseDate().equals(Constants.DEFAULT_VALUE))
                .toList()
                .toFlowable());
    }
}
