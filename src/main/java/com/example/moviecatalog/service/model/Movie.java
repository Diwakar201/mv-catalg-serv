package com.example.moviecatalog.service.model;

public class Movie {
	private String MovieId;
	private String MovieName;
	
	/*
	 * When we want to java to unMarshell from a text to an object using RestTemplate,
	 * It is mandatory to have the public no-arg constructors
	 * because how it works is it first creates an empty instance of the object then populates the values to different 
	 * member variables one by one, so in absense of any empty constructor, it won't be able to create that instance.
	 */
	public Movie() {
		
	}
	
	public Movie(String movieId, String movieName) {
		super();
		MovieId = movieId;
		MovieName = movieName;
	}
	
	public String getMovieId() {
		return MovieId;
	}
	public void setMovieId(String movieId) {
		MovieId = movieId;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	
	
}
