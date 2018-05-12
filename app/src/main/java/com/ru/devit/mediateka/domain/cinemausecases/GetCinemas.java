package com.ru.devit.mediateka.domain.cinemausecases;


import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetCinemas extends UseCase<List<Cinema>> {

    private final CinemaRepository repository;

    public GetCinemas(Scheduler executorThread ,
                      Scheduler uiThread ,
                      CinemaRepository repository){
        super(executorThread , uiThread);
        this.repository = repository;
    }

    @Override
    public Flowable<List<Cinema>> createUseCase() {
        return repository.getCinemas(pageIndex)
                .toFlowable()
                .flatMap(cinemas -> Flowable.fromIterable(cinemas)
                        .filter(cinema -> !cinema.getDescription().isEmpty())
                        .toList()
                        .toFlowable());

    }
}
