package com.ru.devit.mediateka.data.datasource.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ru.devit.mediateka.models.IntArrayConverter;
import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaActorJoinEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;

@Database(entities = {CinemaEntity.class , ActorEntity.class , CinemaActorJoinEntity.class} ,
        version = 1 ,
        exportSchema = false)
@TypeConverters(IntArrayConverter.class)
public abstract class MediatekaDatabase extends RoomDatabase {
    public abstract CinemaDao getCinemaDao();
    public abstract ActorDao getActorDao();
    public abstract CinemaActorJoinDao getCinemaActorJoinDao();
}
