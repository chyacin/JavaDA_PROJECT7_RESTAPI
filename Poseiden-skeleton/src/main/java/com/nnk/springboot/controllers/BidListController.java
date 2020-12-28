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


    @RequestMapping("/bidList/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model) {
        // TODO: call service find all bids to show to the view

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if (loggedInUser != null) {
            model.addAttribute("bidList", bidListService.findAllBidList());
        }

        for (BidList bidList : bidLists) {
            log.info("BidList id: " + bidList.getBidListId() +
                    "BidList date " + bidList.getBidListDate());
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if (loggedInUser != null) {
            bid.setCreationDate(Timestamp.from(Instant.now()));
            bidListService.saveBidList(bid);
            model.addAttribute("bidList", bidListService.findAllBidList());

            log.info("Account: " + bid.getAccount(), "Type: " + bid.getType(),
                    "Bid quantity: " + bid.getBidQuantity());

            return "redirect:/bidList/list";
        }
        if (result.hasErrors()) {
            result.reject("Please enter the correct data");
            log.info("Account error : " + bid.getAccount(), "Type error : " + bid.getType(),
                    "Bid quantity error: " + bid.getBidQuantity());
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);
        BidList bidList = bidListService.findBidListById(id);

        if (loggedInUser != null && bidList != null) {
            model.addAttribute("bidList", bidList);

            for (BidList bid : bidLists){
                log.info("Account: " + bid.getAccount(), "Type: " + bid.getType(),
                        "Bid quantity: " + bid.getBidQuantity());

                return "bidList/update";
            }
        }
        return "redirect:/bidList/list";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if (loggedInUser != null && !result.hasErrors()) {

            BidList bidListById = bidListService.findBidListById(id);
            bidListById.setAccount(bidList.getAccount());
            bidListById.setType(bidList.getType());
            bidListById.setBidQuantity(bidList.getBidQuantity());
            model.addAttribute("bidList", bidListById);
            log.info("Updated BidList" + bidListById.toString());

            return "redirect:/bidList/list";
        }
        if (result.hasErrors()) {
            result.reject("Update was not successfully");
            return "/bidList/update";
        }

        return "bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list

        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);
        BidList bidListById = bidListService.findBidListById(id);

        if (loggedInUser != null && bidListById != null) {
            bidLists.remove(bidListById);
            log.info("Deleted BidList" + bidListById.toString());
            return "redirect:/bidList/list";
        }
        return "bidList/list";
    }
}
