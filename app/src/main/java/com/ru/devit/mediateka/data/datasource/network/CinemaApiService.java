package com.ru.devit.mediateka.data.datasource.network;

import com.ru.devit.mediateka.models.network.ActorDetailResponse;
import com.ru.devit.mediateka.models.network.ActorResponse;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaResponse;
import com.ru.devit.mediateka.models.network.ImagesResponse;

import io.reactivex.Flowable;
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
    Flowable<CinemaResponse> searchCinemas(@Query("query") String searchQuery);

    @GET("search/person")
    Flowable<ActorResponse> searchActors(@Query("query") String searchQuery);

    @GET("movie/{movie_id}/images")
    Single<ImagesResponse> getImagesForCinema(@Path("movie_id") int cinemaId , @Query("include_image_language") String includedLanguages);

    @GET("person/{person_id}/images")
    Single<ImagesResponse> getImagesForActor(@Path("person_id") int actorId);
}
