package com.ru.devit.mediateka.di.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.data.datasource.db.ActorDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaDao;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
@Singleton
public class AppModule {

    private final Context context;
    private final String APP_PREFS = "Application_preferences";

    public AppModule(Context context){
        this.context = context.getApplicationContext();
    }

    @Singleton
    @Provides
    Context provideContext(){
        return context;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreference(){
        return context.getSharedPreferences(APP_PREFS , Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    CinemaActorJoinDao provideCinemaActorJoinDao(){
        MediatekaApp mediatekaApp = (MediatekaApp) context;
        return mediatekaApp.getDatabaseInstance()
                .getCinemaActorJoinDao();
    }

    @Named("executor_thread")
    @Provides
    Scheduler provideExecutorThread(){
        return Schedulers.io();
    }

    @Named("ui_thread")
    @Provides
    Scheduler provideUiThread(){
        return AndroidSchedulers.mainThread();
    }

}
