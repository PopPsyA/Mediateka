package com.ru.devit.mediateka.di.actor;

import dagger.Subcomponent;

@ActorScope
@Subcomponent(modules = ActorModule.class)
public interface ActorComponent {
    ActorListComponent plusActorListComponent(ActorListModule actorListModule);
    ActorDetailComponent plusActorDetailComponent(ActorDetailModule actorDetailModule);
}
