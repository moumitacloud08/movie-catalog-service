package com.samplemicroservice.moviecatalogservice.resources;

import com.samplemicroservice.moviecatalogservice.models.CatalogItem;
import com.samplemicroservice.moviecatalogservice.models.Movie;
import com.samplemicroservice.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        RestTemplate restTemplate = new RestTemplate();

        List<Rating> ratings = Arrays.asList(
                new Rating("1234",4),
                new Rating("5643",5)
        );
        return ratings.stream().map(rating ->
        {   Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(),"Science Fiction",rating.getRating());
        }).collect(Collectors.toList());

    }
}
