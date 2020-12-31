package io.javadev.moviecatalogservice.model;

import java.util.List;

public class UserRating {
    
    public UserRating() {
        super();
    }

    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

}
