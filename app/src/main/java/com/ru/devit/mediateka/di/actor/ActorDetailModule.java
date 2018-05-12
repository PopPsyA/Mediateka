package com.ru.devit.mediateka.di.actor;

import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.actorusecases.GetActorById;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailContentPresenter;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class ActorDetailModule {

    @ActivityScope
    @Provides
    ActorDetailPresenter provideActorDetailPresenter(GetActorById getActorById){
        return new ActorDetailPresenter(getActorById);
    }

    @ActivityScope
    @Provides
    ActorDetailContentPresenter provideActorDetailContentPresenter(){
        return new ActorDetailContentPresenter();
    }

    @ActivityScope
    @Provides
    GetActorById provideGetActorById(@Named("executor_thread")Scheduler executorThread ,
                                     @Named("ui_thread")Scheduler uiThread ,
                                     ActorRepository repository){
        return new GetActorById(executorThread , uiThread , repository);
    }
}
