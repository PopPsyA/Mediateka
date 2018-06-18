package com.ru.devit.mediateka.data.datasource.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CinemaDao {

    @Query("SELECT * FROM CinemaTable WHERE page = :page ORDER BY popularity DESC")
    Single<List<CinemaEntity>> getCinemas(int page);

    @Query("SELECT * FROM CinemaTable WHERE page = :page AND vote_average > 8 ORDER BY vote_average DESC")
    Single<List<CinemaEntity>> getTopRatedCinemas(int page);

    @Query("SELECT * FROM CinemaTable WHERE page = :page AND vote_average = 0 AND release_date >= :currentYear")
    Single<List<CinemaEntity>> getUpComingCinemas(int page , int currentYear);

    @Query("SELECT * FROM CinemaTable WHERE cinemaId = :id")
    Single<CinemaEntity> getCinemaById(final int id);

    @Insert()
    void insertAll(List<CinemaEntity> cinemaEntities);

    @Query("UPDATE CinemaTable SET budget = :budget , revenue = :revenue , cinema_duration = :cinemaDuration , director_name = :directorName " +
            "WHERE cinemaId = :cinemaId")
    void updateCinema(int cinemaId , int budget , int revenue , int cinemaDuration , String directorName );

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertActors(List<ActorEntity> actorEntities);

    @Query("SELECT * FROM CinemaTable WHERE title LIKE :cinemaName")
    Flowable<List<CinemaEntity>> getCinemasByName(String cinemaName);

    @Query("SELECT * FROM CinemaTable WHERE is_favourite")
    Maybe<List<CinemaEntity>> getFavouriteListCinema();

    @Query("UPDATE CinemaTable SET is_favourite = :isFavourite WHERE cinemaId = :cinemaId")
    void insertFavouriteCinema(int cinemaId , boolean isFavourite);
}
