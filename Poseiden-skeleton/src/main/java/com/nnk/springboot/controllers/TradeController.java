package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.TradeServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class TradeController {
    // TODO: Inject Trade service

    @Autowired
    UserService userService;

    @Autowired
    TradeServiceImpl tradeService;

    Logger log = LoggerFactory.getLogger(TradeController.class);


    /**
     * The controller method which gets the home page with all the trades
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url with all the trades
     */
    @RequestMapping("/trade/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model)
    {
        // TODO: find all Trade, add to model
        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);
        List<Trade> tradeList = new ArrayList<>();

        if(loggedInUser != null){
            tradeList = tradeService.findAllTrade();
        }
        model.addAttribute("trade", tradeService.findAllTrade());
        log.info("Total Trade in the list: " + tradeList.size());

        return "trade/list";
    }

    /**
     * The controller method which gets the form where the user can add their trade
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the trade
     * @return the url where you can find the trade form
     */
    @GetMapping("/trade/add")
    public String addTrade(@AuthenticationPrincipal UserDetails username, Trade bid) {

        log.info(username.getUsername() + " loaded a Trade add form " + bid);
        return "trade/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param trade the object that contains the details of the trade
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved trade
     */
    @PostMapping("/trade/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list

        if(result.hasErrors()){
            log.info("Trade errors: " + result.getAllErrors());
            return "trade/add";
        }
        tradeService.saveTrade(trade);
        log.info("Account: " + trade.getAccount() + ", " + "Type: " + trade.getType() + ", " +
                "Buy Quantity: " + trade.getBuyQuantity());

        return "redirect:/trade/list";
    }

    /**
     * The controller method which gets the form where the user can update a trade with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each trade which helps the user identify each trade made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form

        Trade trade = tradeService.findTradeById(id);

        if(trade != null) {
            model.addAttribute("trade", trade);
            log.info("Account: " + trade.getAccount() + ", " + "Type: " + trade.getType() + ", " +
                    "Buy Quantity: " + trade.getBuyQuantity());

            return "trade/update";
        }

        return "redirect:/trade/list";
    }

    /**
     * The controller method which processes the form where the user can update a trade with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each trade which helps the user identify each trade made
     * @param trade the object that contains the details of the trade
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated trade in the trade list
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list

        if(result.hasErrors()){
            log.info("Trade errors: " + result.getAllErrors());
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.updateTrade(trade);

        log.info("Updated Trade name: " + trade.toString() +", "+
                 "Updated Trade name time" + trade.getRevisionDate());

        return "redirect:/trade/list";
    }

    /**
     *The controller method which gets the option where the user can delete a trade
     *The user needs to be logged in
     *If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each trade which helps the user identify each trade made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the trade list without the trade that was deleted
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list

        Trade tradeById = tradeService.findTradeById(id);

        if(tradeById != null){
            log.info("Trade to delete: " + id);
            try{
                tradeService.deleteTrade(id);
            }catch (Exception e){
                System.out.println("error with deleting trade");
            }
            log.info("Deleting Trade: " + tradeById.toString());
            return "redirect:/trade/list";
        }
        return "trade/list";
    }
}
