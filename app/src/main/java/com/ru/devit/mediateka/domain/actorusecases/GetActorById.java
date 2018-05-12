package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class GetActorById extends UseCase<Actor> {

    private final ActorRepository repository;
    private int actorId;

    public GetActorById(Scheduler executorThread ,
                        Scheduler uiThread ,
                        ActorRepository repository){
        super(executorThread , uiThread);
        this.repository = repository;
    }

    public void searchActorById(int actorId) {
        this.actorId = actorId;
    }

    @Override
    protected Flowable<Actor> createUseCase() {
        return repository.getActorById(actorId)
                .toFlowable();
    }
}
