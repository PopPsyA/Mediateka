package com.ru.devit.mediateka.domain.actorusecases;

import com.ru.devit.mediateka.domain.UseCase;
import com.ru.devit.mediateka.models.model.Actor;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetActors extends UseCase<List<Actor>> {


    public GetActors(Scheduler executorThread, Scheduler uiThread) {
        super(executorThread, uiThread);
    }

    @Override
    protected Flowable<List<Actor>> createUseCase() {
        return null;
    }
}
