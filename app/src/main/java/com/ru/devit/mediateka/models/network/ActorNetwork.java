package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

public class ActorNetwork {

	@SerializedName("id") private int actorId;
	@SerializedName("cast_id") private int castId;
	@SerializedName("character") private String character;
	@SerializedName("gender") private int gender;
	@SerializedName("credit_id") private String creditId;
	@SerializedName("name") private String name;
	@SerializedName("profile_path") private String profilePath;
	@SerializedName("order") private int order;
	@SerializedName("popularity") private double popularity;

	public int getCastId(){
		return castId;
	}

	public String getCharacter(){
		return character;
	}

	public int getGender(){
		return gender;
	}

	public String getCreditId(){
		return creditId;
	}

	public String getName(){
		return name;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public int getActorId(){
		return actorId;
	}

	public int getOrder(){
		return order;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
}