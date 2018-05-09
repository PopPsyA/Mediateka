package com.ru.devit.mediateka.di.actor;

import com.ru.devit.mediateka.di.ActivityScope;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailActivity;
import com.ru.devit.mediateka.presentation.actordetail.ActorDetailContentFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActorDetailModule.class)
public interface ActorDetailComponent {

    void inject(ActorDetailActivity actorDetailActivity);

    void inject(ActorDetailContentFragment actorDetailContentFragment);
}
