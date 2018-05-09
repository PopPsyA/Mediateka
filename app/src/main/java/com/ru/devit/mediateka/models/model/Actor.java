package com.ru.devit.mediateka.models.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Actor implements Parcelable {

    private int actorId;
    private int castId;
    private String character;
    private int gender;
    private String creditId;
    private String birthDay;
    private String deathDay;
    private String biography;
    private String name;
    private String profilePath;
    private String profileBackgroundPath;
    private String placeOfBirth;
    private String age;
    private int order;
    private List<Cinema> cinemas;


    public Actor(){}

    protected Actor(Parcel in) {
        actorId = in.readInt();
        name = in.readString();
        profilePath = in.readString();
        biography = in.readString();
        placeOfBirth = in.readString();
        age = in.readString();
        character = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(actorId);
        parcel.writeString(name);
        parcel.writeString(profilePath);
        parcel.writeString(biography);
        parcel.writeString(placeOfBirth);
        parcel.writeString(age);
        parcel.writeString(character);
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    @Override
    public String toString() {
        return String.format("%s %s %s %s" , name , biography , placeOfBirth , birthDay);
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(String deathDay) {
        this.deathDay = deathDay;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfileBackgroundPath() {
        return profileBackgroundPath;
    }

    public void setProfileBackgroundPath(String profileBackgroundPath) {
        this.profileBackgroundPath = profileBackgroundPath;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }
}
