package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class GetActorsByQuery extends UseCase<List<Actor>> {

    private final ActorRepository repository;
    private final FlowableProcessor<String> processor = PublishProcessor.create();

    public GetActorsByQuery(Scheduler executorThread ,
                            Scheduler uiThread ,
                            ActorRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public void setQuery(String query) {
        processor.onNext(query);
    }

    @Override
    protected Flowable<List<Actor>> createUseCase() {

        return processor.debounce(1000 , TimeUnit.MILLISECONDS)
                .filter(query -> !query.isEmpty())
                .distinctUntilChanged()
                .switchMap(repository::searchActors);
    }
}
