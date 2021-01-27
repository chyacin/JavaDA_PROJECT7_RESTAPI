package com.nnk.springboot.controllerITest;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class RatingControllerITest {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private RatingServiceImpl ratingService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("adminOne")
    public void home()throws Exception {

        mockMvc.perform(get("/rating/list"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("adminOne")
    public void addRatingForm()throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("adminOne")
    public void validate()throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setSandPRating("B+");
        rating.setMoodysRating("B");
        rating.setFitchRating("Aaa");
        rating.setOrderNumber(120);

        when(ratingService.saveRating(rating)).thenReturn(rating);

        mockMvc.perform(post("/rating/validate")
                .param("sandPRating", rating.getSandPRating())
                .param("moodysRating", rating.getMoodysRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", rating.getOrderNumber().toString()))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());


        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().size(1));

    }


    @Test
    @WithUserDetails("adminOne")
    public void showUpdateForm()throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setSandPRating("B+");
        rating.setMoodysRating("B");
        rating.setFitchRating("Aaa");
        rating.setOrderNumber(120);

        when(ratingService.findRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/{id}", "1"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithUserDetails("adminOne")
    public void updateRating()throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setSandPRating("C+");
        rating.setMoodysRating("A");
        rating.setFitchRating("Aaa");
        rating.setOrderNumber(120);

        when(ratingService.findRatingById(1)).thenReturn(rating);

        mockMvc.perform(post("/rating/update/{id}", "1")
                .param("sandPRating", rating.getSandPRating())
                .param("moodysRating", rating.getMoodysRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", rating.getOrderNumber().toString()))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());


        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().size(1));

    }


    @Test
    @WithUserDetails("adminOne")
    public void deleteRating()throws Exception {

        Rating rating = new Rating();
        rating.setId(1);
        rating.setSandPRating("C+");
        rating.setMoodysRating("A");
        rating.setFitchRating("Aaa");
        rating.setOrderNumber(120);

        when(ratingService.findRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection());
    }

}
