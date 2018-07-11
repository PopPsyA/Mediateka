package com.ru.devit.mediateka.data.repository.actor;

import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.domain.ActorRepository;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.mapper.ActorDetailEntityToActor;
import com.ru.devit.mediateka.models.mapper.ActorDetailResponseToActor;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ActorRemoteRepository implements ActorRepository {

    private final CinemaApiService apiService;
    private final ActorDetailResponseToActor networkMapper;
    private final ActorDetailEntityToActor dbMapper;
    private final ActorLocalRepository localRepository;
    private final CinemaActorJoinDao cinemaActorJoinDao;

    public ActorRemoteRepository(CinemaApiService apiService,
                                         ActorDetailResponseToActor networkMapper,
                                         ActorDetailEntityToActor dbMapper ,
                                         ActorLocalRepository localRepository ,
                                         CinemaActorJoinDao cinemaActorJoinDao) {
        this.apiService = apiService;
        this.networkMapper = networkMapper;
        this.dbMapper = dbMapper;
        this.localRepository = localRepository;
        this.cinemaActorJoinDao = cinemaActorJoinDao;
    }

    @Override
    public Single<Actor> getActorById(final int actorId) {
        return Single.zip(apiService.getActorById(actorId ,"tagged_images,movie_credits"),
                          apiService.getImagesForActor(actorId) ,
                (response, imagesResponse) -> {
                    response.setImagesResponse(imagesResponse);
                    return networkMapper.map(response);
                })
                .doAfterSuccess(actor -> {
                    localRepository.updateActor(actorId ,
                            actor.getBiography() ,
                            actor.getBirthDay() ,
                            actor.getAge() ,
                            actor.getPlaceOfBirth());
                    localRepository.insertCinemasForActor(dbMapper.mapCinemas(actor.getCinemas()));
                    createRelationBetweenActorAndCinemas(actorId , actor.getCinemas());
                })
                .onErrorResumeNext(throwable -> localRepository.getActorById(actorId));
    }

    @Override
    public Single<List<Actor>> getPopularActors(int page){
        return apiService.getPopularActors(page)
                .map(networkMapper::map)
                .doAfterSuccess(actors -> localRepository.insertActors(dbMapper.map(actors)))
                .onErrorResumeNext(throwable -> localRepository.getPopularActors(page));
    }

    @Override
    public Flowable<List<Actor>> searchActors(String query) {
        return apiService.searchActors(query)
                .map(networkMapper::map)
                .doAfterNext(actors -> localRepository.insertActors(dbMapper.map(actors)))
                .onErrorResumeNext(throwable -> {
                    return localRepository.searchActors("%" + query + "%");
                });
    }

    private void createRelationBetweenActorAndCinemas(final int actorId , List<Cinema> cinemas){
        for (Cinema cinema : cinemas){
            cinemaActorJoinDao.insert(new CinemaActorJoinEntity(cinema.getId() , actorId));
        }
    }
}
