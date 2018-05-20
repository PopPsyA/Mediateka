package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

public class CinemaDetailResponse {
    @SerializedName("adult") private boolean adult;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("budget") private int budget;
    @SerializedName("id") public int id;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("overview") private String overview;
    @SerializedName("popularity") private float popularity;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("revenue") private int revenue;
    @SerializedName("runtime") private int runtime;
    @SerializedName("status") private String status;
    @SerializedName("title") public String title;
    @SerializedName("vote_average") private float voteAverage;
    @SerializedName("vote_count") private int voteCount;
    @SerializedName("genres") private Genres[] genres;
    @SerializedName("credits") private Credits credits;
    private ImagesResponse imagesResponse;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public Credits getCredits(){
        return credits;
    }

    public ImagesResponse getImagesResponse() {
        return imagesResponse;
    }

    public void setImages(ImagesResponse imagesResponse) {
        this.imagesResponse = imagesResponse;
    }

    public class Genres {
        @SerializedName("name") String name;
        @SerializedName("id") int id;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
