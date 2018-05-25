package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.ArrayList;
import java.util.List;

import static com.ru.devit.mediateka.utils.FormatterUtils.DEFAULT_VALUE;
import static com.ru.devit.mediateka.utils.FormatterUtils.defaultValueIfNull;
import static com.ru.devit.mediateka.utils.FormatterUtils.emptyValueIfNull;

public class CinemaEntityToCinema extends Mapper<Cinema, CinemaEntity> {

    @Override
    public CinemaEntity map(final Cinema cinema) {
        final CinemaEntity cinemaEntity = new CinemaEntity();
        fillCinemaEntity(cinemaEntity , cinema);
        return cinemaEntity;
    }

    @Override
    public Cinema reverseMap(final CinemaEntity cinemaEntity) {
        final Cinema cinema = new Cinema();
        fillCinema(cinema , cinemaEntity);
        return cinema;
    }

    public Cinema mapCinemaDetail(CinemaEntity cinemaEntity , List<ActorEntity> actorEntities) {
        final Cinema cinema = new Cinema();
        fillCinema(cinema , cinemaEntity);
        List<Actor> actorList = new ArrayList<>();
        for (ActorEntity actorEntity : actorEntities){
            Actor actor = new Actor();
            fillActor(actor, actorEntity);
            actorList.add(actor);
        }
        cinema.setActors(actorList);
        return cinema;
    }


    public List<ActorEntity> mapActors(List<Actor> actors){
        List<ActorEntity> actorEntities = new ArrayList<>();
        for (Actor actor : actors){
            ActorEntity actorEntity = new ActorEntity();
            fillActorEntity(actorEntity, actor);
            actorEntities.add(actorEntity);
        }
        return actorEntities;
    }

    private void fillActorEntity(ActorEntity actorEntity, Actor actor) {
        actorEntity.setActorId(actor.getActorId());
        actorEntity.setActorName(actor.getName());
        actorEntity.setCharacter(actor.getCharacter());
        actorEntity.setProfilePath(actor.getProfileUrl());
        actorEntity.setOrder(actor.getOrder());
    }

    private void fillActor(Actor actor, ActorEntity actorEntity) {
        actor.setActorId(actorEntity.getActorId());
        actor.setName(actorEntity.getActorName());
        actor.setCharacter(emptyValueIfNull(actorEntity.getCharacter()));
        actor.setProfileUrl(actorEntity.getProfilePath());
        actor.setBiography(actor.getBiography());
        actor.setOrder(actorEntity.getOrder());
    }

    private void fillCinema(Cinema cinema , CinemaEntity cinemaEntity){
        cinema.setId(cinemaEntity.getCinemaId());
        cinema.setPage(cinemaEntity.getPage());
        cinema.setTotalPages(cinemaEntity.getTotalPages());
        cinema.setTotalResults(cinemaEntity.getTotalResults());
        cinema.setAdult(cinemaEntity.isAdult());
        cinema.setDescription(emptyValueIfNull(cinemaEntity.getDescription()));
        cinema.setPosterUrl(cinemaEntity.getPosterUrl());
        cinema.setReleaseDate(cinemaEntity.getReleaseDate().length() == 4 ? DEFAULT_VALUE : cinemaEntity.getReleaseDate());
        cinema.setTitle(cinemaEntity.getTitle());
        cinema.setVoteAverage(cinemaEntity.getVoteAverage());
        cinema.setPopularity(cinemaEntity.getPopularity());
        cinema.setBudget(cinemaEntity.getBudget());
        cinema.setDuration(cinemaEntity.getCinemaDuration());
        cinema.setGenres(cinemaEntity.getGenreIds());
        cinema.setCinemaRevenue(cinemaEntity.getRevenue());
        cinema.setCharacter(cinemaEntity.getActorCharacterName());
        cinema.setDirectorName(defaultValueIfNull(cinemaEntity.getDirectorName()));
    }
    private void fillCinemaEntity(CinemaEntity cinemaEntity , Cinema cinema){
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
        cinemaEntity.setDirectorName(cinema.getDirectorName());
        cinemaEntity.setRevenue(cinema.getCinemaRevenue());
    }

}
