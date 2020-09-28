package com.example.moviecatalog.service.model;

import java.util.List;
/*
 * This is a wrapper class, for future enhancement, always remember when we return a value from an API, we should never use 
 * list as a return value becoz, suppose in future we wanna return a string value as a global value then we cannot do it 
 * using a list, we have to use a class, so for future enhancement also we should return an object of a class even though we
 * are returning just list.
 * As we can see here this class only have a list value as its member, but in future if we want to return username 
 * which can be used globally, so we can just add another field to the class and return the same object by set the name in 
 * the resource. 
 */
public class UserRatings {
	
	private List<Rating> myratings;

	public List<Rating> getMyratings() {
		return myratings;
	}

	public void setMyratings(List<Rating> myratings) {
		this.myratings = myratings;
	}
	
}
