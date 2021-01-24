package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.CurveServiceImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service

    @Autowired
    UserService userService;

    @Autowired
    CurveServiceImpl curvePointService;

    Logger log = LoggerFactory.getLogger(CurveController.class);

    /**
     * The controller method which gets the home page with all the curve points
     * The user needs to be logged in
     * @param username logged in user details(information)
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url with all the curve points
     */
    @RequestMapping("/curvePoint/list")
    public String home(@AuthenticationPrincipal UserDetails username, Model model)
    {
        // TODO: find all Curve Point, add to model
        String loggedInUsername = username.getUsername(); // get logged in username
        User loggedInUser = userService.findUserByUsername(loggedInUsername);
        List<CurvePoint> curvePointList = new ArrayList<>();

        if(loggedInUser != null){
            curvePointList = curvePointService.findAllCurvePoint();

        }
        model.addAttribute("curvePoint", curvePointList);
        log.info("Total Curve points in a List: " + curvePointList.size());

        return "curvePoint/list";
    }

    /**
     * The controller method which gets the form where the user can add their curve points
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param bid the object that contains the details of the curve point
     * @return the url where you can find Curve Point form
     * */
    @GetMapping("/curvePoint/add")
    public String addBidForm(@AuthenticationPrincipal UserDetails username, CurvePoint bid) {

        log.info(username.getUsername() + " loaded a Curve point add form " + bid);
        return "curvePoint/add";
    }

    /**
     * The controller method which check if the data is valid and then save it to the database
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param curvePoint the object that contains the details of the curve point
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the saved curve point
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@AuthenticationPrincipal UserDetails username, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list

        if(result.hasErrors()){
            log.info("Curve point errors: " + result.getAllErrors());
            return "curvePoint/add";
        }
        curvePointService.saveCurvePoint(curvePoint);
        log.info("Curve Id: " + curvePoint.getCurveId() +", "+ "Term: " + curvePoint.getTerm() +", "+
                "Value: " + curvePoint.getValue() +", "+ "Creation date: " + curvePoint.getCreationDate());

        return"redirect:/curvePoint/list";
    }

    /**
     * The controller method which gets the form where the user can update a curve point with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each curve point which helps the user identify each curve point made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the web page with form to be updated
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form

        CurvePoint curvePoint = curvePointService.findCurvePointById(id);

        if(curvePoint != null){
          model.addAttribute("curvePoint", curvePoint);
            log.info("Curve Id: " + curvePoint.getCurveId() +", "+ "Term: " + curvePoint.getTerm() +", "+
                    "Value: " + curvePoint.getValue());
            return "curvePoint/update";

        }
        return"redirect:/curvePoint/list";
    }

    /**
     * The controller method which processes the form where the user can update a curve point with new information
     * The user needs to be logged in
     * If the user isn't logged in, they will be redirected to the login page
     * @param username logged in user details(information)
     * @param id the integer id of each curve point which helps the user identify each curve point made
     * @param curvePoint the object that contains the details of the curve point
     * @param result the validation status of each input field in the form
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the updated curve point in the curve list
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list

        if(result.hasErrors()) {
            log.info("Curve point errors" + result.getAllErrors());
            return "/curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.updateCurvePoint(curvePoint);

        log.info("Updated Curve Point: " + curvePoint.toString() +", "+
                "Updated time for the Curve Point: " + LocalDate.now());

        return "redirect:/curvePoint/list";
    }

    /**
     * The controller method which gets the option where the user can delete a curve point
     * The user needs to be logged in
     * @param username logged in user details(information)
     * @param id the integer id of each curve point which helps the user identify each curve point made
     * @param model a request scoped object injected for us by spring and it's stores attributes.
     * @return the url which redirects and returns the bid list without the bid that was deleted
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@AuthenticationPrincipal UserDetails username, @PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list

        CurvePoint curvePointById = curvePointService.findCurvePointById(id);

        if(curvePointById != null) {
            System.out.println("curve point to delete" + id);
            try {
                curvePointService.deleteCurvePoint(id);
            }catch (Exception e){
                System.out.println("error with deleting curve point");
            }
            log.info("Deleted Curve point: " + curvePointById.toString());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/list";
    }
}
