package com.ru.devit.mediateka.data.datasource.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;

import java.util.List;

@Dao
public interface CinemaActorJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CinemaActorJoinEntity cinemaActorJoinEntity);

    @Query("SELECT * FROM ActorTable INNER JOIN CinemaActorJoinTable " +
            "ON ActorTable.actorId = CinemaActorJoinTable.actor_id " +
            "WHERE CinemaActorJoinTable.cinema_id = :cinemaId ORDER BY `order`" )
    List<ActorEntity> getActorsForCinema(final int cinemaId);

    @Query("SELECT * FROM CinemaTable INNER JOIN CinemaActorJoinTable " +
            "ON CinemaTable.cinemaId = CinemaActorJoinTable.cinema_id " +
            "WHERE CinemaActorJoinTable.actor_id = :actorId")
    List<CinemaEntity> getCinemasForActor(final int actorId);
}
