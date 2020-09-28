package com.example.moviecatalog.service.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.moviecatalog.service.model.CatalogItem;
import com.example.moviecatalog.service.model.Movie;
import com.example.moviecatalog.service.model.Rating;
import com.example.moviecatalog.service.model.UserRatings;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	/*
	 * Basically, we want to have only single instance of RestTemplate class through
	 * out the call so we are using getRestTemplate() in the main class and
	 * used @Bean which ensures it is ran only once.
	 * 
	 * RestTemplate is threadSafe
	 * 
	 */
	@Autowired
	private RestTemplate restTemplate;

	/*
	 * This is part of asynchronous implementation as RestTemplate gonna be
	 * depricated in future so Spring people introduced WebClient which is a part of
	 * reactive programming
	 * 
	 * @Autowired private WebClient.Builder webclientbuilder;
	 */

	// @RequestMapping("/{userId}")
	@RequestMapping(method = RequestMethod.GET, path = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		// get all rated movie Ids -> we have hardcoded the values that the user has
		// rated
		/*
		 * here we are giving the service name which needs to be called instead of complete url
		 * so RestTemplate goes to the service Discovery(Eureka Server) ask for the service name.
		 * Name should be same as what we have given in the application.properties file for each API.
		 */
		UserRatings userRatings= restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRatings.class);

		return userRatings.getMyratings().stream().map(rating -> {

			/*
			 * This is going to be the future as RestTemplate will be depricated in future
			 * webclientBuilder.build().get() -> here we need to specify the method that we
			 * are using for accessing the url bodytoMono means that after retrieving the
			 * value from url convert it to the object of the class which is passed as its
			 * argument Mono means-> its a reactive way of saying that we are getting object
			 * but not right away we want the retrieval to be asynchronous i.e. we assign
			 * some task and then we jump to some other task and tell that whenever this
			 * task completes let me know. We can make entire method for the Asynchronous
			 * type, but since our requirement is we need a list of values so that we can
			 * return it back, so we have to wait till the time the task gets completed, so
			 * to achieve that we are using .block() over the asynchronous call
			 * 
			 * Movie movie=webclientbuilder.build() .get()
			 * .uri("http://localhost:8082/movies/"+rating.getMovieId()) .retrieve()
			 * .bodyToMono(Movie.class) .block();
			 */
			// for each movie Id, call movie info service and get details
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			
			// put them all together
			return new CatalogItem(movie.getMovieName(), "Test Desc", rating.getRating());
		}).collect(Collectors.toList());
		

		

	}

}
