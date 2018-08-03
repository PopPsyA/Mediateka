package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.domain.SystemTimeCalculator;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.model.DateAndTimeInfo;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetCinemaById extends UseCase<Cinema> {

    private int cinemaId;
    private final CinemaRepository repository;
    private final SystemTimeCalculator systemTimeCalculator;

    public GetCinemaById(Scheduler executorThread,
                         Scheduler uiThread,
                         CinemaRepository repository,
                         SystemTimeCalculator systemTimeCalculator){
        super(executorThread , uiThread);
        this.repository = repository;
        this.systemTimeCalculator = systemTimeCalculator;
    }

    public void searchCinemaById(int cinemaId){
        this.cinemaId = cinemaId;
    }

    @Override
    protected Flowable<Cinema> createUseCase() {
        return repository.getCinemaById(cinemaId)
                .toFlowable();
    }

    public boolean isRetrievedTimeMoreThanCurrentTime(DateAndTimeInfo dateAndTimeInfo){
        return systemTimeCalculator.futureTimeInMillisFromDateAndTimeInfo(dateAndTimeInfo) > systemTimeCalculator.currentTimeInMillis();
    }
}
