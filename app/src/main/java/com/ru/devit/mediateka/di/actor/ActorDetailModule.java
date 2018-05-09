package com.ru.devit.mediateka.di.actor;

import android.content.Context;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.data.datasource.db.ActorDao;
import com.ru.devit.mediateka.data.repository.actor.ActorRemoteRepository;
import com.ru.devit.mediateka.data.repository.actor.ActorRepository;
import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.domain.actorusecases.GetActorById;
import com.ru.devit.mediateka.models.mapper.ActorDetailEntityToActor;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailContentPresenter;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailPresenter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

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
