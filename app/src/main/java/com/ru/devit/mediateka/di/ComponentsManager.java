package com.ru.devit.mediateka.di;

import android.content.Context;

import com.ru.devit.mediateka.di.actor.ActorComponent;
import com.ru.devit.mediateka.di.actor.ActorModule;
import com.ru.devit.mediateka.di.application.AppComponent;


import com.ru.devit.mediateka.di.application.DaggerAppComponent;
import com.ru.devit.mediateka.di.cinema.CinemaComponent;
import com.ru.devit.mediateka.di.cinema.CinemaModule;

import com.ru.devit.mediateka.di.application.AppModule;


public class ComponentsManager {

    private AppComponent appComponent;
    private CinemaComponent cinemaComponent;
    private ActorComponent actorComponent;

    private final Context context;

    public ComponentsManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public AppComponent getAppComponent(){
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(context))
                    .build();
        }
        return appComponent;
    }

    public CinemaComponent plusCinemaComponent(){
        if (cinemaComponent == null){
            cinemaComponent = appComponent.plusCinemaComponent(new CinemaModule());
        }
        return cinemaComponent;
    }

    public ActorComponent plusActorComponent(){
        if (actorComponent == null){
            actorComponent = appComponent.plusActorComponent(new ActorModule());
        }
        return actorComponent;
    }

    public void clearActorComponent(){
        actorComponent = null;
    }

    public void clearCinemaComponent(){
        cinemaComponent = null;
    }


}
