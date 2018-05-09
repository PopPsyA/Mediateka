package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.data.repository.cinema.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetCinemaById extends UseCase<Cinema> {

    private final CinemaRepository repository;
    private int cinemaId;

    public GetCinemaById(Scheduler executorThread ,
                         Scheduler uiThread ,
                         CinemaRepository repository){
        super(executorThread , uiThread);
        this.repository = repository;
    }

    public void searchCinemaById(int cinemaId){
        this.cinemaId = cinemaId;
    }


    @Override
    protected Flowable<Cinema> createUseCase() {
        return repository.getCinemaById(cinemaId)
                .toFlowable();
    }
}
