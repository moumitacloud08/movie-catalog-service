package com.samplemicroservice.moviecatalogservice.resources;

import com.samplemicroservice.moviecatalogservice.models.CatalogItem;
import com.samplemicroservice.moviecatalogservice.models.Movie;
import com.samplemicroservice.moviecatalogservice.models.Rating;
import com.samplemicroservice.moviecatalogservice.models.UserRating;
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

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {


//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234",4),
//                new Rating("5643",5)
//        );
        UserRating ratings =  restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId,UserRating.class);
        return ratings.getRatings().stream().map(rating ->
        {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(),"Science Fiction",rating.getRating());
        }).collect(Collectors.toList());

    }
}
