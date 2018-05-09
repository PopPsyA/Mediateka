package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.data.repository.actor.ActorRemoteRepository;
import com.ru.devit.mediateka.data.repository.actor.ActorRepository;
import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import javax.inject.Inject;
import javax.inject.Named;

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
