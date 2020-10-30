package com.samplemicroservice.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import com.samplemicroservice.moviecatalogservice.models.CatalogItem;
import com.samplemicroservice.moviecatalogservice.models.Movie;
import com.samplemicroservice.moviecatalogservice.models.Rating;
import com.samplemicroservice.moviecatalogservice.models.UserRating;
import com.samplemicroservice.moviecatalogservice.service.MovieInfo;
import com.samplemicroservice.moviecatalogservice.service.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    MovieInfo movieInfo;
    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings.getRatings().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());
    }






}
