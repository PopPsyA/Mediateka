package com.ru.devit.mediateka.data.datasource.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;

@Database(entities = {CinemaEntity.class , ActorEntity.class , CinemaActorJoinEntity.class} ,
        version = 1 ,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CinemaDao getCinemaDao();
    public abstract ActorDao getActorDao();
    public abstract CinemaActorJoinDao getCinemaActorJoinDao();
}
