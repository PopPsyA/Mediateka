package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.FormatterUtils;

import java.util.ArrayList;
import java.util.List;


import static com.ru.devit.mediateka.utils.FormatterUtils.defaultValueIfNull;
import static com.ru.devit.mediateka.utils.FormatterUtils.emptyValueIfNull;

public class ActorDetailEntityToActor extends Mapper<Actor , ActorEntity> {

    @Override
    public ActorEntity map(final Actor value) {
        final ActorEntity actorEntity = new ActorEntity();
        fillActorEntity(actorEntity, value);
        return actorEntity;
    }

    @Override
    public Actor reverseMap(final ActorEntity value) {
        final Actor actor = new Actor();
        fillActor(actor, value);
        return actor;
    }

    public List<CinemaEntity> mapCinemas(List<Cinema> cinemas){
        if (cinemas != null){
            List<CinemaEntity> cinemaEntities = new ArrayList<>();
            for (Cinema cinema : cinemas){
                CinemaEntity cinemaEntity = new CinemaEntity();
                fillCinemaEntity(cinemaEntity, cinema);
                cinemaEntities.add(cinemaEntity);
            }
            return cinemaEntities;
        }
        return new ArrayList<>();
    }

    public Actor mapDetailActor(final ActorEntity value , final List<CinemaEntity> cinemaEntities){
        final Actor actor = new Actor();
        fillActor(actor , value);
        final List<Cinema> cinemas = new ArrayList<>(cinemaEntities.size());
        for (CinemaEntity cinemaEntity : cinemaEntities){
            Cinema cinema = new Cinema();
            fillCinema(cinema, cinemaEntity);
            cinemas.add(cinema);
        }
        actor.setCinemas(cinemas);
        return actor;
    }

    private void fillActor(Actor actor, ActorEntity value) {
        actor.setActorId(value.getActorId());
        actor.setName(emptyValueIfNull(value.getActorName()));
        actor.setAge(defaultValueIfNull(value.getAge()));
        actor.setBirthDay(emptyValueIfNull(value.getBirthDay()));
        actor.setBiography(emptyValueIfNull(value.getBiography()));
        actor.setBirthDay(defaultValueIfNull(value.getBirthDay()));
        actor.setPlaceOfBirth(defaultValueIfNull(value.getPlaceOfBirth()));
        actor.setProfileUrl(value.getPlaceOfBirth());
        actor.setPopularity(value.getPopularity());
    }

    private void fillActorEntity(ActorEntity actorEntity, Actor value) {
        actorEntity.setActorId(value.getActorId());
        actorEntity.setActorName(value.getName());
        actorEntity.setAge(value.getAge());
        actorEntity.setProfilePath(value.getProfileUrl());
        actorEntity.setBiography(value.getBiography());
        actorEntity.setBirthDay(value.getBirthDay());
        actorEntity.setPlaceOfBirth(value.getPlaceOfBirth());
        actorEntity.setCastId(value.getCastId());
        actorEntity.setPopularity(value.getPopularity());
    }

    private void fillCinema(Cinema cinema , CinemaEntity cinemaEntity) {
        cinema.setId(cinemaEntity.getCinemaId());
        cinema.setPage(cinemaEntity.getPage());
        cinema.setTotalPages(cinemaEntity.getTotalPages());
        cinema.setTotalResults(cinemaEntity.getTotalResults());
        cinema.setAdult(cinemaEntity.isAdult());
        cinema.setDescription(cinemaEntity.getDescription());
        cinema.setPosterUrl(cinemaEntity.getPosterUrl());
        cinema.setReleaseDate(FormatterUtils.getYearFromDate(cinemaEntity.getReleaseDate()));
        cinema.setTitle(cinemaEntity.getTitle());
        cinema.setVoteAverage(cinemaEntity.getVoteAverage());
        cinema.setPopularity(cinemaEntity.getPopularity());
        cinema.setBudget(cinemaEntity.getBudget());
        cinema.setDuration(cinemaEntity.getCinemaDuration());
        cinema.setGenres(cinemaEntity.getGenreIds());
        cinema.setCharacter(cinemaEntity.getActorCharacterName());
    }

    private void fillCinemaEntity(CinemaEntity cinemaEntity , Cinema cinema) {
        cinemaEntity.setCinemaId(cinema.getId());
        cinemaEntity.setPage(cinema.getPage());
        cinemaEntity.setTotalPages(cinema.getTotalPages());
        cinemaEntity.setTotalResults(cinema.getTotalResults());
        cinemaEntity.setAdult(cinema.isAdult());
        cinemaEntity.setDescription(cinema.getDescription());
        cinemaEntity.setPosterUrl(cinema.getPosterUrl());
        cinemaEntity.setReleaseDate(cinema.getReleaseDate());
        cinemaEntity.setTitle(cinema.getTitle());
        cinemaEntity.setVoteAverage(cinema.getVoteAverage());
        cinemaEntity.setPopularity(cinema.getPopularity());
        cinemaEntity.setBudget(cinema.getBudget());
        cinemaEntity.setGenreIds(cinema.getGenres());
        cinemaEntity.setCinemaDuration(cinema.getDuration());
        cinemaEntity.setActorCharacterName(cinema.getCharacter());
    }
}
