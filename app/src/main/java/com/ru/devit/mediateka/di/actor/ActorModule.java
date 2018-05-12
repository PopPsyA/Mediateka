package com.ru.devit.mediateka.di.actor;

import android.content.Context;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.data.datasource.db.ActorDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.data.repository.actor.ActorLocalRepository;
import com.ru.devit.mediateka.data.repository.actor.ActorRemoteRepository;
import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.models.mapper.ActorDetailEntityToActor;
import com.ru.devit.mediateka.models.mapper.ActorDetailResponseToActor;

import dagger.Module;
import dagger.Provides;

@Module
public class ActorModule {

    @ActorScope
    @Provides
    ActorRepository provideRepository(CinemaApiService apiService ,
                                      ActorDetailResponseToActor networkMapper ,
                                      ActorDetailEntityToActor dbMapper ,
                                      ActorLocalRepository cache ,
                                      CinemaActorJoinDao cinemaActorJoinDao){
        return new ActorRemoteRepository(apiService , networkMapper , dbMapper , cache , cinemaActorJoinDao);
    }

    @ActorScope
    @Provides
    ActorLocalRepository provideActorLocalRepository(ActorDao actorDao ,
                                                ActorDetailEntityToActor mapper ,
                                                CinemaActorJoinDao cinemaActorJoinDao){
        return new ActorLocalRepository(actorDao , mapper , cinemaActorJoinDao);
    }

    @ActorScope
    @Provides
    ActorDao providesActorDao(Context context){
        MediatekaApp mediatekaApp = (MediatekaApp) context;
        return mediatekaApp.getDatabaseInstance()
                .getActorDao();
    }

    @ActorScope
    @Provides
    ActorDetailEntityToActor provideActorDetailEntityToActor(){
        return new ActorDetailEntityToActor();
    }

    @ActorScope
    @Provides
    ActorDetailResponseToActor provideActorDetailResponseToActor(){
        return new ActorDetailResponseToActor();
    }

}
