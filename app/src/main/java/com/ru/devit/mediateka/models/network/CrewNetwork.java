package com.ru.devit.mediateka.models.network;

import com.google.gson.annotations.SerializedName;

public class CrewNetwork{

//	@SerializedName("gender")
//	private int gender;
//
//	@SerializedName("credit_id")
//	private String creditId;

	@SerializedName("name")
	private String name;

//	@SerializedName("profile_path")
//	private String profilePath;
//
//	@SerializedName("id")
//	private int id;
//
//	@SerializedName("department")
//	private String department;

	@SerializedName("job")
	private String job;

//	public int getGender(){
//		return gender;
//	}
//
//	public String getCreditId(){
//		return creditId;
//	}

	public String getName(){
		return name;
	}

//	public String getProfilePath(){
//		return profilePath;
//	}
//
//	public int getActorId(){
//		return id;
//	}
//
//	public String getDepartment(){
//		return department;
//	}

	public String getJob(){
		return job;
	}
}