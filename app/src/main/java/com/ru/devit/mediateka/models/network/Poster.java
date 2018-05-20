package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

public class Poster {
    @SerializedName("file_path") private String posterUrl;
    public String getPosterUrl() {
            return posterUrl;
        }
}
