package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListServiceImpl;
import com.nnk.springboot.services.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
public class BidListController {
    // TODO: Inject Bid service

    @Autowired
    UserService userService;

    @Autowired
    BidListServiceImpl bidListService;

    Logger log = LoggerFactory.getLogger(BidListController.class);
    private final List<BidList> bidLists = new ArrayList<>();


    /**
     * The controller method which gets the home page with all the bids
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url with all the bids
     */
    @RequestMapping("/bidList/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model) {
        // TODO: call service find all bids to show to the view

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if (loggedInUser != null) {
            model.addAttribute("bidList", bidListService.findAllBidList());
        }

            log.info("Total Bids in List: " + bidLists.size());


        return "bidList/list";
    }

    /**
     * The controller method which gets the form where the user can add their bids
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the bid
     * @return the url where you can find the added the bidList form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(@AuthenticationPrincipal UserDetails username, BidList bid) {
        return "bidList/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the bid
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved bid list
     */
    @PostMapping("/bidList/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list

        if (result.hasErrors()) {
            log.info("errors : " + result.getAllErrors());
            return "bidList/add";
        }
        bid.setCreationDate(Timestamp.from(Instant.now()));
        bidListService.saveBidList(bid);
        log.info("Account: " + bid.getAccount(), "Type: " + bid.getType(),
                "Bid quantity: " + bid.getBidQuantity());


        return "redirect:/bidList/list";
    }

    /**
     * The controller method which gets the form where the user can update a bid with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each bid which helps the user identify each bid made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form

        BidList bidList = bidListService.findBidListById(id);

        if (bidList != null) {
            model.addAttribute("bidList", bidList);
                log.info("Account: " + bidList.getAccount(), "Type: " + bidList.getType(),
                        "Bid quantity: " + bidList.getBidQuantity());

                return "bidList/update";
            }
        return "redirect:/bidList/list";
    }

    /**
     *  The controller method which processes the form where the user can update a bid with new information
     *  The user needs to be logged in
     *  If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each bid which helps the user identify each bid made
     * @param bidList the object that contains the details of the bidList
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated bid in the bid list
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid

        if (result.hasErrors()) {
            log.info("bid errors : " + result.getAllErrors());
            return "/bidList/update";
        }
            bidList.setBidListId(id);
            bidListService.updateBidList(bidList);

            log.info("Updated BidList" + bidList.toString(),
                    "Update bidList time" + Timestamp.from(Instant.now()));

            return "redirect:/bidList/list";
    }

    /**
     *  The controller method which gets the option where the user can delete a bid
     *  The user needs to be logged in
     * @param username logged in user details(information)
     * @param id the integer id of each bid which helps the user identify each bid made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the bid list without the bid that was deleted
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list

        BidList bidListById = bidListService.findBidListById(id);

        if (bidListById != null) {
            System.out.println("Bid list to delete" + id);
          try {
              bidListService.deleteBidList(id);
          }catch (Exception e){
              System.out.println("error with deleting bid");
          }
            log.info("Deleted BidList" + bidListById.toString());
            return "redirect:/bidList/list";
        }
        return "bidList/list";
    }
}
