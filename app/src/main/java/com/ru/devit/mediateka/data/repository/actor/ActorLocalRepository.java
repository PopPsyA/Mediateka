package com.ru.devit.mediateka.data.repository.actor;

import com.ru.devit.mediateka.data.datasource.db.ActorDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.mapper.ActorDetailEntityToActor;
import com.ru.devit.mediateka.models.model.Actor;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ActorLocalRepository implements ActorRepository {

    private final ActorDao actorDao;
    private final ActorDetailEntityToActor mapper;
    private final CinemaActorJoinDao cinemaActorJoinDao;

    public ActorLocalRepository(ActorDao actorDao ,
                                ActorDetailEntityToActor mapper ,
                                CinemaActorJoinDao cinemaActorJoinDao) {
        this.actorDao = actorDao;
        this.mapper = mapper;
        this.cinemaActorJoinDao = cinemaActorJoinDao;
    }

    @Override
    public Single<Actor> getActorById(final int actorId) {
        return actorDao.getActorById(actorId)
                .map(actorEntity -> mapper.mapDetailActor(actorEntity ,
                        cinemaActorJoinDao.getCinemasForActor(actorId)));
    }

    @Override
    public Flowable<List<Actor>> searchActors(String query) {
        return actorDao.getAllActorsByName(query)
                .map(mapper::reverseMap);

    }

    @Override
    public Single<List<Actor>> getPopularActors(int page){
        return actorDao.getPopularActors() //TODO FIX THIS PAGE
                .map(mapper::reverseMap);
    }

    public void insertCinemasForActor(List<CinemaEntity> cinemaEntities) {
        actorDao.insertCinemas(cinemaEntities);
    }

    public void updateActor(int actorId, String biography, String birthDay, String age, String placeOfBirth) {
        actorDao.updateActor(actorId , biography , birthDay , age , placeOfBirth);
    }

    public void insertActors(List<ActorEntity> actorEntities){
        actorDao.insertActors(actorEntities);
    }
}
