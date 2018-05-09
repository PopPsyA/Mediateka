package com.ru.devit.mediateka.di.actor;


import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.presentation.actorlist.ActorsFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActorListModule.class)
public interface ActorListComponent {
    void inject(ActorsFragment actorsFragment);
}
