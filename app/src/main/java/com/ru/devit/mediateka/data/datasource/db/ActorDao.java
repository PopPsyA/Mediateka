package com.ru.devit.mediateka.data.datasource.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.*;

@Dao
public interface ActorDao {

    @Query("SELECT * FROM ActorTable WHERE  actorId = :actorId")
    Single<ActorEntity> getActorById(final int actorId);

    @Query("SELECT * FROM ActorTable")
    List<ActorEntity> getAllActors();

    @Query("SELECT * FROM ActorTable WHERE actorName LIKE :actorName")
    Flowable<List<ActorEntity>> getAllActorsByName(final String actorName);

    @Insert(onConflict = REPLACE)
    void insertActors(List<ActorEntity> actorEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCinemas(List<CinemaEntity> cinemaEntities);

    @Query("UPDATE ActorTable SET biography = :biography , birthDay = :birthDay , age = :age , placeOfBirth = :placeOfBirth " +
            "WHERE actorId = :actorId")
    void updateActor(int actorId , String biography, String birthDay, String age, String placeOfBirth);

    @Query("SELECT * FROM ActorTable ORDER BY popularity DESC")
    Single<List<ActorEntity>> getPopularActors();
}
