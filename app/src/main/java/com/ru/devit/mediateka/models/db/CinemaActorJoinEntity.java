package com.ru.devit.mediateka.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(
        tableName = "CinemaActorJoinTable" ,
        primaryKeys = {"cinema_id" , "actor_id"} ,
        foreignKeys = {
                @ForeignKey(entity = CinemaEntity.class ,
                        parentColumns = "cinemaId" ,
                        childColumns = "cinema_id") ,
                @ForeignKey(entity = ActorEntity.class ,
                        parentColumns = "actorId",
                        childColumns = "actor_id")
        })

public class CinemaActorJoinEntity {
    @ColumnInfo(name = "cinema_id") private final int cinemaId;
    @ColumnInfo(name = "actor_id") private final int actorId;

    public CinemaActorJoinEntity(final int cinemaId , final int actorId) {
        this.cinemaId = cinemaId;
        this.actorId = actorId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public int getActorId() {
        return actorId;
    }
}
