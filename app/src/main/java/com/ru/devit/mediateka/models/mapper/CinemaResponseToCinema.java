package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.network.ActorNetwork;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaNetwork;
import com.ru.devit.mediateka.models.network.CinemaResponse;
import com.ru.devit.mediateka.models.network.CrewNetwork;
import com.ru.devit.mediateka.models.network.Poster;
import com.ru.devit.mediateka.utils.FormatterUtils;

import java.util.ArrayList;
import java.util.List;

public class CinemaResponseToCinema{

    public List<Cinema> map(CinemaResponse cinemaResponse) {
        List<Cinema> cinemas = new ArrayList<>();
        for (CinemaNetwork response : cinemaResponse.getCinemas()){
            Cinema cinema = new Cinema();
            cinema.setPage(cinemaResponse.getPage());
            cinema.setTotalPages(cinemaResponse.getTotalPages());
            cinema.setTotalResults(cinemaResponse.getTotalResults());
            cinema.setId(response.getId());
            cinema.setVoteAverage(response.getVoteAverage());
            cinema.setTitle(response.getTitle());
            cinema.setAdult(response.isAdult());
            cinema.setDescription(response.getDescription());
            cinema.setPosterUrl(response.getPosterUrl());
            cinema.setReleaseDate(response.getReleaseDate());
            cinema.setPopularity(response.getPopularity());
            cinema.setGenres(FormatterUtils.formatGenres(response.getGenreIds()));
            cinemas.add(cinema);
        }
        return cinemas;
    }

    public Cinema map(CinemaDetailResponse response){
        Cinema cinema = new Cinema();
        cinema.setId(response.getId());
        cinema.setTitle(response.getTitle());
        cinema.setAdult(response.isAdult());
        cinema.setDuration(response.getRuntime());
        cinema.setBudget(response.getBudget());
        cinema.setOriginalTitle(response.getOriginalTitle());
        cinema.setDescription(response.getOverview());
        cinema.setStatus(response.getStatus());
        cinema.setPosterUrl(response.getPosterUrl());
        cinema.setReleaseDate(response.getReleaseDate());
        cinema.setBackdropUrl(response.getBackdropPath());
        cinema.setPopularity(response.getPopularity());
        cinema.setVoteAverage(response.getVoteAverage());
        cinema.setCinemaRevenue(response.getRevenue());
        setGenres(response , cinema);
        setActors(response , cinema);
        setDirectorName(response , cinema);
        setPosters(response , cinema);
        return cinema;
    }

    private void setGenres(CinemaDetailResponse response , Cinema cinema){
        int[] ids = new int[response.getGenres().length];
        for (int i = 0; i < response.getGenres().length; i++){
            CinemaDetailResponse.Genres[] genres = response.getGenres();
            ids[i] = genres[i].getId();
        }
        cinema.setGenres(FormatterUtils.formatGenres(ids));
    }

    private void setActors(CinemaDetailResponse response , Cinema cinema){
        List<Actor> actors = new ArrayList<>();
        for (ActorNetwork cast : response.getCredits().getCast()){
            Actor actor = new Actor();
            actor.setActorId(cast.getActorId());
            actor.setName(cast.getName());
            actor.setCastId(cast.getCastId());
            actor.setCharacter(cast.getCharacter());
            actor.setProfileUrl(cast.getProfilePath());
            actor.setOrder(cast.getOrder());
            actors.add(actor);
        }
        cinema.setActors(actors);
    }

    private void setDirectorName(CinemaDetailResponse response , Cinema cinema){
        for (CrewNetwork crew : response.getCredits().getCrews()){
            if (crew.getJob().equals("Director")){
                cinema.setDirectorName(FormatterUtils.defaultValueIfNull(crew.getName()));
                break;
            }
        }
    }

    private void setPosters(CinemaDetailResponse response , Cinema cinema){
        final List<String> posterUrls = new ArrayList<>();
        final List<String> backdropUrls = new ArrayList<>(8);
        for (final Poster poster : response.getImagesResponse().getCinemaPosters()){
            posterUrls.add(poster.getPosterUrl());
        }
        for (final Poster poster : response.getImagesResponse().getCinemaBackgroundPosters()){
            backdropUrls.add(poster.getPosterUrl());
            if (backdropUrls.size() >= 8){
                break;
            }
        }
        cinema.setBackdropUrls(backdropUrls);
        cinema.setPosterUrls(posterUrls);
    }
}
