package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {
    @SerializedName("posters") private List<Poster> posters;

    public List<Poster> getPosters() {
        return posters;
    }
}
