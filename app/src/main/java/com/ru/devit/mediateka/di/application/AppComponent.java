package com.ru.devit.mediateka.di.application;

import com.ru.devit.mediateka.di.actor.ActorComponent;
import com.ru.devit.mediateka.di.cinema.CinemaComponent;
import com.ru.devit.mediateka.di.actor.ActorModule;
import com.ru.devit.mediateka.di.cinema.CinemaModule;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailActivity;
import com.ru.devit.mediateka.presentation.main.MainActivity;
import com.ru.devit.mediateka.presentation.search.SearchActivity;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailContentFragment;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;
import com.ru.devit.mediateka.presentation.cinemadetail.CinemaDetailContentFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class , AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(SearchActivity searchActivity);

    CinemaComponent plusCinemaComponent(CinemaModule cinemaModule);
    ActorComponent plusActorComponent(ActorModule actorModule);
}
