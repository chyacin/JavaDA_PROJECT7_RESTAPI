package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{

	@Autowired
	UserService userService;

	Logger log = LoggerFactory.getLogger(HomeController.class);

	/**
	 * The controller method which gets the user their home page
	 * The user needs to be logged in
	 * If the user isn't logged in, they will be redirected to the login page
	 * @param username logged in user details(information)
	 * @param model a request scoped object injected for us by spring and it's stores attributes.
	 * @return the url where the user can find the home page
	 */
	@RequestMapping("/")
	public String home(@AuthenticationPrincipal UserDetails username, Model model)
	{
		String loggedInUsername = username.getUsername(); // get logged in username
		User loggedInUser = userService.findUserByUsername(loggedInUsername);

		return "home";
	}


	/**
	 * The controller method which gets the admin their home page
	 * The user needs to be logged in
	 * If the user isn't logged in, they will be redirected to the login page
	 * @param username logged in user details(information)
	 * @param model a request scoped object injected for us by spring and it's stores attributes.
	 * @return the url which redirects and returns the bidList in a list
	 */
	@RequestMapping("/admin/home")
	public String adminHome(@AuthenticationPrincipal UserDetails username, Model model)
	{
		if(username != null && username.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){

			return "redirect:/bidList/list";
		}
		return "app/error";
	}


}
