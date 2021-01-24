package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * The controller method which gets the user the login page
     * @return the login page
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * The controller method where the admin can view, edit or delete a user
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @return the url returns the list of users for this application
     */

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles(@AuthenticationPrincipal UserDetails username) {
        ModelAndView mav = new ModelAndView();
        if(username != null && username.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            mav.addObject("users", userRepository.findAll());
            mav.setViewName("user/list");
        }
        else{
            mav.setViewName("403");
        }
        return mav;
    }


    /**
     * The controller method which denies an unauthorized user access to a page where only authorized admin are allowed to access
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @return the url returns a page with information stating that the user doesn't have access
     */
    @GetMapping("/error")
    public ModelAndView error(@AuthenticationPrincipal UserDetails username) {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
