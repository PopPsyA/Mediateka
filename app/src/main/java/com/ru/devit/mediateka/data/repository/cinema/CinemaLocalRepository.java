package com.ru.devit.mediateka.data.repository.cinema;

import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaDao;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public class CinemaLocalRepository implements CinemaRepository {
    private final CinemaDao cinemaDao;
    private final CinemaActorJoinDao cinemaActorJoinDao;
    private CinemaMapper mapper;

    private SingleTransformer<List<CinemaEntity> , List<Cinema>> mapCinemas = upstream ->
            upstream.map(cinemaEntities -> mapper.getCinemaEntityToCinema().reverseMap(cinemaEntities));

    public CinemaLocalRepository(CinemaDao cinemaDao ,
                                  CinemaMapper mapper ,
                                  CinemaActorJoinDao cinemaActorJoinDao) {
        this.cinemaDao = cinemaDao;
        this.mapper = mapper;
        this.cinemaActorJoinDao = cinemaActorJoinDao;
    }

    @Override
    public Single<List<Cinema>> getCinemas(int pageIndex) {
        return cinemaDao.getCinemas(pageIndex)
                .compose(mapCinemas);
    }

    @Override
    public Single<List<Cinema>> getTopRatedCinemas(int pageIndex) {
        return cinemaDao.getTopRatedCinemas(pageIndex)
                .compose(mapCinemas);
    }

    @Override
    public Single<List<Cinema>> getUpComingCinemas(int pageIndex) {
        return cinemaDao.getUpComingCinemas(pageIndex)
                .compose(mapCinemas);
    }

    void insertCinemas(List<CinemaEntity> cinemaEntities){
        cinemaDao.insertAll(cinemaEntities);
    }

    void insertActors(List<ActorEntity> actors){
        cinemaDao.insertActors(actors);
    }

    @Override
    public Single<Cinema> getCinemaById(final int cinemaId) {
        return cinemaDao.getCinemaById(cinemaId)
                .map(cinemaEntity -> mapper.getCinemaEntityToCinema().mapCinemaDetail(cinemaEntity ,
                    cinemaActorJoinDao.getActorsForCinema(cinemaId)));
    }

    @Override
    public Flowable<List<Cinema>> searchCinemas(String query) {
        return cinemaDao.getCinemasByName(query)
                .map(cinemaEntities -> mapper.getCinemaEntityToCinema().reverseMap(cinemaEntities));

    }

    void updateCinema(int cinemaId , int budget , int revenue , int cinemaDuration , String directorName) {
        cinemaDao.updateCinema(cinemaId , budget , revenue , cinemaDuration , directorName);
    }

}
