package com.ru.devit.mediateka.di.actor;

import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.actorusecases.GetActorsByQuery;
import com.ru.devit.mediateka.presentation.actorlist.ActorsPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class ActorListModule {

    @ActivityScope
    @Provides
    ActorsPresenter provideActorsPresenter(GetActorsByQuery getActorsByQuery){
        return new ActorsPresenter(getActorsByQuery);
    }

    @ActivityScope
    @Provides
    GetActorsByQuery provideGetActorByQuery(@Named("executor_thread") Scheduler executorThread ,
                                            @Named("ui_thread") Scheduler uiThread ,
                                            ActorRepository repository){
        return new GetActorsByQuery(executorThread , uiThread , repository);
    }
}
