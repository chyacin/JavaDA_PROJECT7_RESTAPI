package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RatingServiceImpl;
import com.nnk.springboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RatingController {
    // TODO: Inject Rating service

    @Autowired
    UserService userService;

    @Autowired
    RatingServiceImpl ratingService;

    Logger log = LoggerFactory.getLogger(RatingController.class);
    private final List<Rating> ratingList = new ArrayList<>();

    /**
     * The controller method which gets the home page with all the ratings
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return  the url with all the ratings
     */
    @RequestMapping("/rating/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model)
    {
        // TODO: find all Rating, add to model
        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if(loggedInUser != null){
            model.addAttribute("rating", ratingService.findAllRating());
        }
        log.info("Total ratings in the list: " + ratingList.size());

        return "rating/list";
    }

    /**
     * The controller method which gets the form where the user can add their ratings
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param rating the object that contains the details of the ratings
     * @return the url where you can find the rating form
     */
    @GetMapping("/rating/add")
    public String addRatingForm(@AuthenticationPrincipal UserDetails username, Rating rating) {
        return "rating/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param rating the object that contains the details of the rating
     * @param result the validation status of each input field in the form
     * @param model  a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved rating
     */
    @PostMapping("/rating/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list

        if(result.hasErrors()) {
            log.info("Rating errors: " + result.getAllErrors());
            return "rating/add";
        }
        ratingService.saveRating(rating);
        log.info("Fitch rating: " + rating.getFitchRating(), "Moody's Rating: " + rating.getMoodysRating(),
                "Sand P rating: " + rating.getSandPRating(), "Order number: " + rating.getOrderNumber());

        return "redirect:/rating/list";
    }

    /**
     * The controller method which gets the form where the user can update a rating with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each rating which helps the user identify each rating made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form

        Rating rating = ratingService.findRatingById(id);

        if(rating != null) {
            model.addAttribute("rating", rating);
            log.info("Fitch rating: " + rating.getFitchRating(), "Moody's Rating: " + rating.getMoodysRating(),
                    "Sand P rating: " + rating.getSandPRating(), "Order number: " + rating.getOrderNumber());
            return "rating/update";
        }
        return "redirect:/rating/list";
    }

    /**
     * The controller method which processes the form where the user can update a rating with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each rating which helps the user identify each rating made
     * @param rating the object that contains the details of the rating
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated rating in the rating list
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list

        if(result.hasErrors()){
            log.info("rating errors: " + result.getAllErrors());
            return "/rating/update";
        }
        rating.setId(id);
        ratingService.updateRating(rating);

        log.info("Updated Rating: " + rating.toString(),
                "Updated Rating time" + Timestamp.from(Instant.now()));

        return "redirect:/rating/list";
    }

    /**
     * The controller method which gets the option where the user can delete a rating
     * The user needs to be logged in
     * @param username logged in user details(information)
     * @param id the integer id of each rating which helps the user identify each rating made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the bid list without the rating that was deleted
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list

        Rating ratingById = ratingService.findRatingById(id);

        if(ratingById != null){
            log.info("Rating to delete: " + id);
          try {
              ratingService.deleteRating(id);
          }catch(Exception e){
              System.out.println("error with deleting rating");
          }
          log.info("Deleted Rating: " + ratingById.toString());
            return "redirect:/rating/list";
        }
        return "rating/list";
    }
}
