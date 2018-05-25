package com.ru.devit.mediateka.models.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Cinema implements Parcelable {
    private int cinemaId;
    private String description;
    private String posterUrl;
    private boolean isAdult;
    private int page;
    private int totalPages;
    private int totalResults;
    private String backdropUrl;
    private int budget;
    private String homepage;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private float popularity;
    private String releaseDate;
    private int revenue;
    private int duration;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private float voteAverage;
    private int voteCount;
    private String genres;
    private String directorName;
    private String character;
    private List<Actor> actors;
    private List<String> posterUrls;
    private List<String> backdropUrls;

    public Cinema(){}

    protected Cinema(Parcel in) {
        title = in.readString();
        voteAverage = in.readFloat();
        description = in.readString();
        budget = in.readInt();
        releaseDate = in.readString();
        directorName = in.readString();
        character = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeFloat(voteAverage);
        parcel.writeString(description);
        parcel.writeInt(budget);
        parcel.writeString(releaseDate);
        parcel.writeString(directorName);
        parcel.writeString(character);
    }

    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj instanceof Cinema){
            Cinema cinema = (Cinema) obj;
            return this.title.equals(cinema.getTitle());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + cinemaId;
        result = prime * result + title.length();
        result = prime * result + description.length();
        return result;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getCinemaRevenue() {
        return revenue;
    }

    public void setCinemaRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
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

    public int getId() {
        return cinemaId;
    }

    public void setId(int cinemaId) {
        this.cinemaId = cinemaId;
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
        return voteAverage >= 10 ? 0 : voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<String> getPosterUrls() {
        return posterUrls;
    }

    public void setPosterUrls(List<String> posterUrls) {
        this.posterUrls = posterUrls;
    }

    public List<String> getBackdropUrls() {
        return backdropUrls;
    }

    public void setBackdropUrls(List<String> backdropUrls) {
        this.backdropUrls = backdropUrls;
    }
}
