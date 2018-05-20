package com.ru.devit.mediateka.models.network;

import java.util.List;
import com.google.gson.annotations.SerializedName;
public class ActorDetailResponse{

	@SerializedName("birthday") private String birthday;
	@SerializedName("also_known_as") private List<String> alsoKnownAs;
	@SerializedName("gender") private int gender;
	@SerializedName("imdb_id") private String imdbId;
	@SerializedName("profile_path") private String profilePath;
	@SerializedName("biography") private String biography;
	@SerializedName("deathday") private Object deathday;
	@SerializedName("place_of_birth") private String placeOfBirth;
	@SerializedName("popularity") private double popularity;
	@SerializedName("name") private String name;
	@SerializedName("id") private int id;
	@SerializedName("adult") private boolean adult;
	@SerializedName("homepage") private Object homepage;
	@SerializedName("tagged_images") private TaggedImages taggedImages;
	@SerializedName("movie_credits") private MovieCredits movieCredits;
	private ImagesResponse imagesResponse;

	public String getBirthday(){
		return birthday;
	}

	public List<String> getAlsoKnownAs(){
		return alsoKnownAs;
	}

	public int getGender(){
		return gender;
	}

	public String getImdbId(){
		return imdbId;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public String getBiography(){
		return biography;
	}

	public Object getDeathday(){
		return deathday;
	}

	public String getPlaceOfBirth(){
		return placeOfBirth;
	}

	public double getPopularity(){
		return popularity;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}

	public Object getHomepage(){
		return homepage;
	}

	public TaggedImages getTaggedImages() {
		return taggedImages;
	}

	public MovieCredits getMovieCredits() {
		return movieCredits;
	}

	public ImagesResponse getImagesResponse() {
		return imagesResponse;
	}

	public void setImagesResponse(ImagesResponse imagesResponse) {
		this.imagesResponse = imagesResponse;
	}

	public class TaggedImages {
		@SerializedName("results") List<ImageResult> imageResults;

		public List<ImageResult> getImageResults() {
			return imageResults;
		}
	}

	public class ImageResult {
		@SerializedName("file_path") String backgroundPoster;

		public String getBackgroundPoster() {
			return backgroundPoster;
		}
	}

	public class MovieCredits {
		@SerializedName("cast") List<CinemaNetwork> cinemas;
		public List<CinemaNetwork> getCinemas() {
			return cinemas;
		}
	}
}