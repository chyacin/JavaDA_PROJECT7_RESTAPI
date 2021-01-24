package com.nnk.springboot.controllers;

import com.nnk.springboot.config.ValidPassword;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Logger log = LoggerFactory.getLogger(UserController.class);



    /**
     * The controller method which gets the home page with all the users
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url with all the users
     */
    @RequestMapping("/user/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model)
    {
        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);
        List<User> userList = new ArrayList<>();

        if(loggedInUser != null){
            userList = userService.findAllUsers();
        }
        model.addAttribute("users", userService.findAllUsers());
        log.info("Total Users in List: " + userList.size());

        return "user/list";
    }

    /**
     * The controller method which gets the form where the admin can add a new user
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the user
     * @return the url where you can find the user form
     */
    @GetMapping("/user/add")
    public String addUser(@AuthenticationPrincipal UserDetails username, User bid) {

        log.info(username.getUsername() + " loaded a User add form " + bid);
        return "user/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param user the object that contains the details of the user
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved user
     */
    @RolesAllowed("ADMIN")
    @PostMapping("/user/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid User user, BindingResult result, Model model) {

        if(result.hasErrors()){
            log.info("User errors: "  + result.getAllErrors());
            return "user/add";
        }

        userService.saveUser(user);
        model.addAttribute("users", userService.findAllUsers());

        log.info("Full name: " + user.getFullname() + ", " + "Username: " + user.getUsername() + ", " +
                "Role name: " + user.getRole());

        return "redirect:/user/list";

    }

    /**
     * The controller method which gets the form where the admin can update a user with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each user
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {

        User user = userService.findUserById(id);

        if(user != null){
            user.setPassword("");
            model.addAttribute("users", user);
            log.info("Full name: " + user.getFullname() + ", " + "Username: " + user.getUsername() + ", " +
                    "Role name: " + user.getRole());
            return "user/update";
        }
        return "redirect:/user/list";
    }

    /**
     * The controller method which processes the form where the admin can update a user with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each user
     * @param user the object that contains the details of the user
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated user in the user list
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.info("User errors: " + result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("users", userService.findAllUsers());

        log.info("Updated User: " + user.toString() +", "+
                  "Updated time: " + LocalDate.now());
        return "redirect:/user/list";
    }

    /**
     * The controller method which gets the option where the admin can delete user
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each user
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the user list without the user that was deleted
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        User userById = userService.findUserById(id);

        if(userById != null){
            log.info("User to delete: " + id);
            try{
                userService.deleteUser(id);
                model.addAttribute("users", userService.findAllUsers());
            }catch (Exception e){
                System.out.println("error with deleting user");
            }
            log.info("Deleting User: " + userById.toString());
            return "redirect:/user/list";
        }
        return "user/list";
    }

}
