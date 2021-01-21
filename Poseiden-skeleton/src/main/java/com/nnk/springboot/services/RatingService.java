package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {

    public Rating saveRating(Rating rating);
    public Rating findRatingById(int id);
    List<Rating> findAllRating();
    public void updateRating(Rating rating);
    public void deleteRating(int id);

}
