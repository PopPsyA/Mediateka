package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CinemaDetailResponse {
    @SerializedName("adult") private boolean adult;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("budget") private int budget;
    @SerializedName("homepage") private String homepage;
    @SerializedName("id") public int id;
    @SerializedName("imdb_id") private String imdbId;
    @SerializedName("original_language") private String originalLanguage;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("overview") private String overview;
    @SerializedName("popularity") private float popularity;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("revenue") private int revenue;
    @SerializedName("runtime") private int runtime;
    @SerializedName("status") private String status;
    @SerializedName("tagline") private String tagline;
    @SerializedName("title") public String title;
    @SerializedName("video") private boolean video;
    @SerializedName("vote_average") private float voteAverage;
    @SerializedName("vote_count") private int voteCount;
    @SerializedName("genres") private Genres[] genres;
    @SerializedName("credits") private Credits credits;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
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

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
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
