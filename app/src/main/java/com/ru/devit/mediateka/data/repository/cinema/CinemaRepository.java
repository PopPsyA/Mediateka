package com.ru.devit.mediateka.data.repository.cinema;

import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CinemaRepository {

    Single<List<Cinema>> getCinemas(int pageIndex);
    Single<List<Cinema>> getTopRatedCinemas(int pageIndex);
    Single<List<Cinema>> getUpComingCinemas(int pageIndex);
    Single<Cinema> getCinemaById(int cinemaId);
    Flowable<List<Cinema>> searchCinemas(String query);
}
