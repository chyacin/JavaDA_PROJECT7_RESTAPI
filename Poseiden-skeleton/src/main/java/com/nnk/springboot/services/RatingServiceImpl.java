package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * The service method which saves the rating to the database
     * @param rating the rating to be saved
     * @return the save rating object
     */
    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * The service method which finds the respective rating by their corresponding id
     * @param id the id of the rating
     * @return the found rating
     */
    @Override
    public Rating findRatingById(int id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        if(ratingOptional.isPresent()){
            Rating rating = ratingOptional.get();
            return rating;
        }
        return null;
    }

    /**
     * The service method which finds and returns all the ratings in a list
     * @return the list of all the ratings
     */
    @Override
    public List<Rating> findAllRating() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings;
    }

    /**
     * The service method which updates the user's rating
     * @param rating the rating to be updated
     */
    @Override
    public void updateRating(Rating rating) {
        Rating updatedRating = findRatingById(rating.getId());
        if(updatedRating != null){
            updatedRating.setFitchRating(rating.getFitchRating());
            updatedRating.setMoodysRating(rating.getMoodysRating());
            updatedRating.setSandPRating(rating.getSandPRating());
            updatedRating.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(updatedRating);
        }

    }

    /**
     * The service method which deletes the user's rating
     * @param id the id of the rating
     */
    @Override
    public void deleteRating(int id) {
        ratingRepository.deleteById(id);

    }
}
