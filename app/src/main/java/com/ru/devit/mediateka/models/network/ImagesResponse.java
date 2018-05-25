package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {
    @SerializedName("posters") private List<Poster> cinemaPosters;
    @SerializedName("profiles") private List<Poster> actorPosters; // only for actor !!!
    @SerializedName("backdrops") private List<Poster> cinemaBackgroundPosters;

    public List<Poster> getCinemaPosters() {
        return cinemaPosters;
    }

    public List<Poster> getActorPosters() {
        return actorPosters;
    }

    public List<Poster> getCinemaBackgroundPosters() {
        return cinemaBackgroundPosters;
    }
}
