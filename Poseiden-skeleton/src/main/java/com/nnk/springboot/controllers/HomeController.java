package com.nnk.springboot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{

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
		return "redirect:/bidList/list";
	}


}
