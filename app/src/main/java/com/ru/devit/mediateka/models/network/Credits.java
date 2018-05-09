package com.ru.devit.mediateka.models.network;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Credits{

	@SerializedName("cast") private List<ActorNetwork> casts;
	@SerializedName("crew") private List<CrewNetwork> crews;

	public List<ActorNetwork> getCast(){
		return casts;
	}

	public List<CrewNetwork> getCrews(){
		return crews;
	}
}