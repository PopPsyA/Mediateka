package com.ru.devit.mediateka.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.ru.devit.mediateka.models.IntArrayConverter;

import java.util.List;

@Entity(tableName = "CinemaTable")
public class CinemaEntity {

    @PrimaryKey private int cinemaId;
    @ColumnInfo(name = "page") private int page;
    @ColumnInfo(name = "total_pages") private int totalPages;
    @ColumnInfo(name = "total_results") private int totalResults;
    @ColumnInfo(name = "description") private String description;
    @ColumnInfo(name = "poster_url") private String posterUrl;
    @ColumnInfo(name = "is_adult") private boolean isAdult;
    @ColumnInfo(name = "title") private String title;
    @ColumnInfo(name = "release_date") private String releaseDate;
    @ColumnInfo(name = "vote_average") private float voteAverage;
    @ColumnInfo(name = "popularity") private float popularity;
    @ColumnInfo(name = "budget") private int budget;
    @ColumnInfo(name = "genre_ids") private int[] genreIds;
    @ColumnInfo(name = "director_name") private String directorName;
    @ColumnInfo(name = "actor_character") private String actorCharacterName;
    @ColumnInfo(name = "cinema_duration") private int cinemaDuration;
    @ColumnInfo(name = "revenue") private int revenue;

    public CinemaEntity(){}

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getCinemaId(){
        return cinemaId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setCinemaDuration(int cinemaDuration) {
        this.cinemaDuration = cinemaDuration;
    }

    public int getCinemaDuration() {
        return cinemaDuration;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getActorCharacterName() {
        return actorCharacterName;
    }

    public void setActorCharacterName(String actorCharacterName) {
        this.actorCharacterName = actorCharacterName;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
