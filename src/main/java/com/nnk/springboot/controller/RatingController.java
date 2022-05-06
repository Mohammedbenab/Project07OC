package com.nnk.springboot.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;

import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.service.UserService;

@Controller
public class RatingController {
	
	 // TODO: Inject rating service

    @Autowired
    private RatingService ratingService;
    
	@Autowired
	private UserService userService;
    

    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal) throws Exception {
		model.addAttribute("ratingList", ratingService.getList());
    	User userPrincipal = userService.getUserByUsername(principal.getName());
    	String principalUser = "";
    	if(userPrincipal != null) {
    		principalUser = userPrincipal.getRole();
    	}else principalUser = "USER";
    	
		model.addAttribute("principal",principalUser);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating Rating) {
    	
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, RedirectAttributes redirect){
        // TODO: check data valid and save to db, after saving return rating list
    	if(!result.hasErrors()) {
	    	try {
				ratingService.add(rating);
				model.addAttribute("ratingList", ratingService.getList());
				redirect.addFlashAttribute("messageSuccess", "Rating added successfully !");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
				return "redirect:/rating/add";
			}
    	}
    	return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: get rating by Id and to model then show to the form
		try {
			Rating rating = ratingService.getById(id);
			model.addAttribute("rating", rating);
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model, RedirectAttributes redirect) {
        // TODO: check required fields, if valid call service to update rating and return list rating
    	if (result.hasErrors()) {
            return "rating/update";
        }
    	try {
    		ratingService.update(rating);
	    	model.addAttribute("ratingList", ratingService.getList());
	    	redirect.addFlashAttribute("messageSuccess", "Rating was update by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
    	return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: Find rating by Id and delete the bid, return to Bid list
    	try {
    		Rating rating = ratingService.getById(id);
    		ratingService.delete(rating);
			model.addAttribute("rating", ratingService.getList());
			redirect.addFlashAttribute("messageSuccess", "Rating was delete by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "redirect:/rating/list";
    }

}
