package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.RuleNameServiceImpl;
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
public class RuleNameController {
    // TODO: Inject RuleName service

    @Autowired
    UserService userService;

    @Autowired
    RuleNameServiceImpl ruleNameService;

    Logger log = LoggerFactory.getLogger(RuleNameController.class);
    private final List<RuleName> ruleNameList = new ArrayList<>();

    /**
     * The controller method which gets the home page with all the rule names
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url with all the rule names
     */
    @RequestMapping("/ruleName/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model) {
        // TODO: find all RuleName, add to model
        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);

        if (loggedInUser != null) {
            model.addAttribute("ruleName", ruleNameService.findAllRuleName());
        }

        log.info("Total Rule Name in List: " + ruleNameList.size());

        return "ruleName/list";
    }

    /**
     * The controller method which gets the form where the user can add their rule name
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the rule name
     * @return the url where you can find the rule name form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(@AuthenticationPrincipal UserDetails username, RuleName bid) {
        return "ruleName/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param ruleName the object that contains the details of the ruleName
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved rule name
     */
    @PostMapping("/ruleName/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list

        if (result.hasErrors()) {
            log.info("Rule name errors: " + result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.saveRuleName(ruleName);
        log.info("Name: " + ruleName.getName(), "Description: " + ruleName.getDescription(),
                "json: " + ruleName.getJson(), "template: " + ruleName.getTemplate(),
                "sql: " + ruleName.getSqlStr(), "sqlPart" + ruleName.getSqlPart());

        return "redirect:/ruleName/list";
    }

    /**
     * The controller method which gets the form where the user can update a rule name with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each rule name which helps the user identify each rule name made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form

        RuleName ruleName = ruleNameService.findRuleNameById(id);

        if (ruleName != null) {
            model.addAttribute("ruleName", ruleName);
            log.info("Name: " + ruleName.getName(), "Description: " + ruleName.getDescription(),
                    "json: " + ruleName.getJson(), "template: " + ruleName.getTemplate(),
                    "sql: " + ruleName.getSqlStr(), "sqlPart" + ruleName.getSqlPart());

            return "ruleName/update";
        }

        return "redirect:/ruleName/list";
    }

    /**
     * The controller method which processes the form where the user can update a rule name with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each rule name which helps the user identify each rule name made
     * @param ruleName the object that contains the details of the ruleName
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated rule name in the rule name list
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list

        if (result.hasErrors()) {
            log.info("rule name errors: " + result.getAllErrors());
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.updateRuleName(ruleName);

        log.info("Updated Rule name" + ruleName.toString(),
                "Update rule name time" + Timestamp.from(Instant.now()));

        return "redirect:/ruleName/list";
    }

    /**
     * The controller method which gets the option where the user can delete a rule name
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each rule name which helps the user identify each rule name made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the rule name list without the rule name that was deleted
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list

        RuleName ruleNameById = ruleNameService.findRuleNameById(id);

        if (ruleNameById != null) {
            log.info("Rule name to delete: " + id);
            try {
                ruleNameService.deleteRuleName(id);
            } catch (Exception e) {
                System.out.println("error with deleting rule name");
            }
            log.info("Deleting Rule name: " + ruleNameById.toString());
            return "redirect:/ruleName/list";

        }
        return "ruleName/list";
    }
}
