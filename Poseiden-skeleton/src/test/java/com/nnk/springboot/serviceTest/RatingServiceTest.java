package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RatingServiceTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;


    @Test
    public void saveRating_returnRating() {

        //arrange
        Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        rating.setId(1);

        when(ratingRepository.save(rating)).thenReturn(rating);

        //act
        Rating savedRating = ratingService.saveRating(rating);

        //assert
        assertNotNull(savedRating);
        assertEquals(rating.getId(), savedRating.getId(), 0);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

        @Test
        public void findRatingById_returnId(){

           //arrange
            Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
            rating.setId(1);

            when(ratingRepository.findById(1)).thenReturn(java.util.Optional.of(rating));

            //act
            Rating findRatingById = ratingService.findRatingById(1);

            //assert
            assertEquals(rating.getId(), findRatingById.getId(), 0);
        }

    @Test
    public void findAllRatings_returnFindAllRatings(){

        //arrange
        Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        rating.setId(1);

        Rating rating1 = new Rating("moodysRating1", "sandPRating1", "fitchRating1", 2);
        rating1.setId(2);

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating, rating1));

        //act
        List<Rating> ratings = ratingService.findAllRating();

        //assert
        assertEquals(2, ratings.size());
        assertEquals(1, ratings.get(0).getId(),0);
        assertEquals(2, ratings.get(1).getId(),0 );
        Assert.assertTrue(ratings.size() > 0);
    }


    @Test
    public void updateRating_updateRating(){

        //arrange
        Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(java.util.Optional.of(rating));

        //act
        ratingService.updateRating(rating);

        //assert
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void deleteRating_deleteRating(){

        //act
        ratingService.deleteRating(1);

        //assert
        Optional<Rating> rating = ratingRepository.findById(1);
        Assert.assertFalse(rating.isPresent());
    }

}
