package com.example.moviecatalog.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * Here the Eureka clients are talking to Eureka Server as Eureka Server is deployed on Default port
 * so, thats why EurekaServer is able to detect EurekaClient, if deployed on other port, then the detials need to be 
 * mentioned in the application.properties
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {
	
	
	/*
	 * @LoadBalance is playing that major role, where the client(Movie-Catalog-Service) is calling the Eureka Server(which is
	 * another Spring Boot Application) using spring abstraction (making prgrammer life little easy)- so programmer is just 
	 * adding the annotation and spring manages everything internally. Spring Cloud user Client Side management where everything
	 * regarding calling the eureka server is done at the client side only
	 * 
	 * So basically, by adding @LoadBalanced what are telling RestTemplate do not go to service directly, whatever url I  am
	 * giving is not the actual url, it is just the hint about what service you need to discover 
	 * and look for the service in the servicediscovery, so now RestTemplate is going to look for the hint, which
	 * service to call -> which is provided by tellling the service name which we gave in the application.properties file
	 * Here this annotation not only does service discovery, it also does load balancing at the client side but not at server
	 * side.
	 * 
	 * Fault Tollerance is also managed by Eureka Server, so every Eureka Client sends "Heart-beats" to Eureka Server
	 * telling the the service is up and running. Now what happens when Service Discovery Erureka Sever gets down, then Eureka
	 * Client will use the cache, and all these things are managed internally by Eureka, we are not doing anything explicitly.
	 * These all things are managed by @LoadBalanced
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/*
	 * This is part of WebClient Builder
	 * 
	 * @Bean public WebClient.Builder getWebClientBuilder() { return
	 * WebClient.builder(); }
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
