package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CinemaNetwork {
    @SerializedName("id") private int id;
    @SerializedName("overview") private String description;
    @SerializedName("poster_path") private String posterUrl;
    @SerializedName("adult") private boolean isAdult;
    @SerializedName("title") private String title;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("vote_average") private float voteAverage;
    @SerializedName("popularity") private float popularity;
    @SerializedName("genre_ids") private int[] genreIds;
    @SerializedName("character") private String character;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public boolean isAdult() {
        return isAdult;
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

    public float getVoteAverage() {
        return voteAverage;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public float getPopularity() {
        return popularity;
    }
    public String getCharacter() {
        return character;
    }
}
