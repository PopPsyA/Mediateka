package com.ru.devit.mediateka.domain.cinemausecases;

import com.ru.devit.mediateka.data.repository.cinema.CinemaRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class GetCinemaByQuery extends UseCase<List<Cinema>> {

    private final CinemaRepository repository;
    private final FlowableProcessor<String> processor = PublishProcessor.create();

    @Inject public GetCinemaByQuery(Scheduler executorThread ,
                                    Scheduler uiThread ,
                                    CinemaRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public void setQuery(String query) {
        processor.onNext(query);
    }

    @Override
    protected Flowable<List<Cinema>> createUseCase() {
        return processor.debounce(1000 , TimeUnit.MILLISECONDS)
                .filter(q -> !q.isEmpty())
                .distinctUntilChanged()
                .switchMap(repository::searchCinemas);
    }
}
