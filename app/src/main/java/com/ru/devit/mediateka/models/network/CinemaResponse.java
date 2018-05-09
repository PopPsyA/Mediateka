package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CinemaResponse {
    @SerializedName("page") private int page;
    @SerializedName("total_pages") private int totalPages;
    @SerializedName("total_results") private int totalResults;
    @SerializedName("results") private List<CinemaNetwork> cinemas;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<CinemaNetwork> getCinemas() {
        return cinemas;
    }
}
