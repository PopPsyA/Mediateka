package com.ru.devit.mediateka.data.repository.cinema;

import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaResponse;
import com.ru.devit.mediateka.models.network.ImagesResponse;

import java.util.List;
import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;


public class CinemaRemoteRepository implements CinemaRepository {

    private final CinemaLocalRepository cinemaCache;
    private final CinemaMapper mapper;
    private final CinemaApiService apiService;
    private final CinemaActorJoinDao cinemaActorJoinDao;

    public CinemaRemoteRepository(CinemaLocalRepository cinemaCache ,
                                          CinemaApiService apiService ,
                                          CinemaMapper mapper ,
                                          CinemaActorJoinDao cinemaActorJoinDao) {
        this.cinemaCache = cinemaCache;
        this.apiService = apiService;
        this.mapper = mapper;
        this.cinemaActorJoinDao = cinemaActorJoinDao;
    }

    @Override
    public Single<List<Cinema>> getCinemas(final int pageIndex) {
        return apiService.getCinemas(pageIndex)
                .compose(cacheCinemas())
                .onErrorResumeNext(throwable -> cinemaCache.getCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getTopRatedCinemas(final int pageIndex) {
        return apiService.getTopRatedCinemas(pageIndex)
                .compose(cacheCinemas())
                .onErrorResumeNext(throwable -> cinemaCache.getTopRatedCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getUpComingCinemas(final int pageIndex) {
        return apiService.getUpComingCinemas(pageIndex)
                .compose(cacheCinemas())
                .onErrorResumeNext(throwable -> cinemaCache.getUpComingCinemas(pageIndex));
    }

    @Override
    public Single<Cinema> getCinemaById(final int cinemaId) {
        return Single.zip(apiService.getCinemaById(cinemaId , "credits"),
                          apiService.getImagesForCinema(cinemaId , "en,null") , // null , because it written in documentation(null == all other language's)
                (cinemaDetailResponse, imagesResponse) -> {
                    cinemaDetailResponse.setImages(imagesResponse);
                    return mapper.map(cinemaDetailResponse);
                })
                .doAfterSuccess(cinema -> {
                    cinemaCache.updateCinema(cinemaId ,
                            cinema.getBudget() ,
                            cinema.getCinemaRevenue() ,
                            cinema.getDuration() ,
                            cinema.getDirectorName());
                    cinemaCache.insertActors(mapper.mapActors(cinema.getActors()));
                    createRelationBetweenCinemaAndActors(cinemaId , cinema.getActors());
                })
                .onErrorResumeNext(throwable -> cinemaCache.getCinemaById(cinemaId));
    }

    @Override
    public Flowable<List<Cinema>> searchCinemas(String query) {
        return apiService.searchCinemas(query)
                .map(mapper::map)
                .doAfterNext(cinemas -> cinemaCache.insertCinemas(mapper.map(cinemas)))
                .onErrorResumeNext(throwable -> {
                    return cinemaCache.searchCinemas("%" + query + "%");
                });
    }

    private SingleTransformer<CinemaResponse , List<Cinema>> cacheCinemas(){
        return upstream ->
                upstream.map(mapper::map)
                        .doAfterSuccess(cinemas -> {
                            cinemaCache.insertCinemas(mapper.map(cinemas));
                        });
    }

    private void createRelationBetweenCinemaAndActors(final int cinemaId , final List<Actor> actors){
        for (Actor actor : actors){
            cinemaActorJoinDao.insert(new CinemaActorJoinEntity(cinemaId , actor.getActorId()));
        }
    }
}
