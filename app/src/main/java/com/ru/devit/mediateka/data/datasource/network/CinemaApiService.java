package com.ru.devit.mediateka.data.datasource.network;

import com.ru.devit.mediateka.models.network.ActorDetailResponse;
import com.ru.devit.mediateka.models.network.ActorNetwork;
import com.ru.devit.mediateka.models.network.ActorResponse;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CinemaApiService {

    @GET("movie/popular")
    Single<CinemaResponse> getCinemas(@Query("page") int pageIndex);

    @GET("movie/top_rated")
    Single<CinemaResponse> getTopRatedCinemas(@Query("page") int pageIndex);

    @GET("movie/upcoming")
    Single<CinemaResponse> getUpComingCinemas(@Query("page") int pageIndex);

    @GET("movie/{movie_id}")
    Single<CinemaDetailResponse> getCinemaById(@Path("movie_id") int movieId , @Query("append_to_response") String appendToResponse);

    @GET("person/{person_id}")
    Single<ActorDetailResponse> getActorById(@Path("person_id") int actorId , @Query("append_to_response") String appendToResponse);

    @GET("person/popular")
    Single<ActorResponse> getPopularActors(@Query("page") int pageIndex);

    @GET("search/movie")
    Flowable<CinemaResponse> searchCinemas(@Query("query") String query);

    @GET("search/person")
    Flowable<ActorResponse> searchActors(@Query("query") String query);
}
