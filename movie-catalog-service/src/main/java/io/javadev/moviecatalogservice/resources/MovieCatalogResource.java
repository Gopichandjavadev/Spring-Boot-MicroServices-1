package io.javadev.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javadev.moviecatalogservice.model.CatalogItem;
import io.javadev.moviecatalogservice.model.Movie;
import io.javadev.moviecatalogservice.model.Rating;
import io.javadev.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
  
    @Autowired
    private RestTemplate template;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
      
        List<Rating> ratings = new ArrayList<>();
        Rating rating1 = new Rating("bahu", 3);
        Rating rating2 = new Rating("Saho", 4);
        ratings.add(rating1);
        ratings.add(rating2);
        
        UserRating userRatings = template.getForObject("http://ratings-data-service/ratingsData/users/" + userId, UserRating.class);
        
        return userRatings.getRatings().stream().map(rating->{
            Movie[] movies = template.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie[].class);
            return new CatalogItem(movies[0].getName(), "desc", rating.getRating());
        }).collect(Collectors.toList());
    }
}
