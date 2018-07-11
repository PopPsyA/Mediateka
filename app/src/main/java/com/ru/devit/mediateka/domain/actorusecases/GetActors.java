package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetActors extends UseCase<List<Actor>> {

    private final ActorRepository repository;

    @Inject public GetActors(Scheduler executorThread, Scheduler uiThread, ActorRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<Actor>> createUseCase() {
        return repository.getPopularActors(1)
                .toFlowable();
    }
}
