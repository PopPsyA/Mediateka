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
import io.reactivex.SingleTransformer;


public class CinemaRemoteRepository implements CinemaRepository {

    private CinemaLocalRepository cinemaCache;
    private CinemaMapper mapper;
    private final CinemaApiService apiService;
    private final CinemaActorJoinDao cinemaActorJoinDao;
    private static final String LOCAL_LANGUAGE = Locale.getDefault().getLanguage();

    private SingleTransformer<CinemaResponse , List<Cinema>> cacheCinemas = upstream ->
             upstream.map(mapper::map)
            .doAfterSuccess(cinemas -> {
                cinemaCache.insertCinemas(mapper.map(cinemas));
            });


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
        return apiService.getCinemas(LOCAL_LANGUAGE , pageIndex)
                .compose(cacheCinemas)
                .onErrorResumeNext(throwable -> cinemaCache.getCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getTopRatedCinemas(final int pageIndex) {
        return apiService.getTopRatedCinemas(LOCAL_LANGUAGE , pageIndex)
                .compose(cacheCinemas)
                .onErrorResumeNext(throwable -> cinemaCache.getTopRatedCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getUpComingCinemas(final int pageIndex) {
        return apiService.getUpComingCinemas(LOCAL_LANGUAGE , pageIndex)
                .compose(cacheCinemas)
                .onErrorResumeNext(throwable -> cinemaCache.getUpComingCinemas(pageIndex));
    }

    @Override
    public Single<Cinema> getCinemaById(final int cinemaId) {
        return Single.zip(apiService.getCinemaById(cinemaId, LOCAL_LANGUAGE , "credits"),
                          apiService.getImagesForCinema(cinemaId) ,
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
        return apiService.searchCinemas(LOCAL_LANGUAGE , query)
                .map(mapper::map)
                .doAfterNext(cinemas -> cinemaCache.insertCinemas(mapper.map(cinemas)))
                .onErrorResumeNext(throwable -> {
                    return cinemaCache.searchCinemas("%" + query + "%");
                });
    }

    private void createRelationBetweenCinemaAndActors(final int cinemaId , final List<Actor> actors){
        for (Actor actor : actors){
            cinemaActorJoinDao.insert(new CinemaActorJoinEntity(cinemaId , actor.getActorId()));
        }
    }
}
