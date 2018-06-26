package com.ru.devit.mediateka.data.repository.cinema;

import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.network.CinemaResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;


public class CinemaRemoteRepository implements CinemaRepository {

    private final CinemaLocalRepository cinemaLocalRepository;
    private final CinemaMapper mapper;
    private final CinemaApiService apiService;
    private final CinemaActorJoinDao cinemaActorJoinDao;

    public CinemaRemoteRepository(CinemaLocalRepository cinemaLocalRepository,
                                  CinemaApiService apiService ,
                                  CinemaMapper mapper ,
                                  CinemaActorJoinDao cinemaActorJoinDao) {
        this.cinemaLocalRepository = cinemaLocalRepository;
        this.apiService = apiService;
        this.mapper = mapper;
        this.cinemaActorJoinDao = cinemaActorJoinDao;
    }

    @Override
    public Single<List<Cinema>> getCinemas(final int pageIndex) {
        return apiService.getCinemas(pageIndex)
                .compose(saveToDatabaseCinemaList())
                .onErrorResumeNext(throwable -> cinemaLocalRepository.getCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getTopRatedCinemas(final int pageIndex) {
        return apiService.getTopRatedCinemas(pageIndex)
                .compose(saveToDatabaseCinemaList())
                .onErrorResumeNext(throwable -> cinemaLocalRepository.getTopRatedCinemas(pageIndex));
    }

    @Override
    public Single<List<Cinema>> getUpComingCinemas(final int pageIndex) {
        return apiService.getUpComingCinemas(pageIndex)
                .compose(saveToDatabaseCinemaList())
                .onErrorResumeNext(throwable -> cinemaLocalRepository.getUpComingCinemas(pageIndex));
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
                    cinemaLocalRepository.updateCinema(cinemaId ,
                            cinema.getBudget() ,
                            cinema.getCinemaRevenue() ,
                            cinema.getDuration() ,
                            cinema.getDirectorName());
                    cinemaLocalRepository.insertActors(mapper.mapActors(cinema.getActors()));
                    createRelationBetweenCinemaAndActors(cinemaId , cinema.getActors());
                })
                .onErrorResumeNext(throwable -> cinemaLocalRepository.getCinemaById(cinemaId));
    }

    @Override
    public Flowable<List<Cinema>> searchCinemas(String query) {
        return apiService.searchCinemas(query)
                .map(mapper::map)
                .doAfterNext(cinemas -> cinemaLocalRepository.insertCinemas(mapper.map(cinemas)))
                .onErrorResumeNext(throwable -> {
                    return cinemaLocalRepository.searchCinemas("%" + query + "%");
                });
    }

    private SingleTransformer<CinemaResponse , List<Cinema>> saveToDatabaseCinemaList(){
        return upstream ->
                upstream.map(mapper::map)
                        .doAfterSuccess(cinemas -> {
                            cinemaLocalRepository.insertCinemas(mapper.map(cinemas));
                        });
    }

    private void createRelationBetweenCinemaAndActors(final int cinemaId , final List<Actor> actors){
        for (Actor actor : actors){
            cinemaActorJoinDao.insert(new CinemaActorJoinEntity(cinemaId , actor.getActorId()));
        }
    }
}
