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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.UserService;

@Controller
public class BidListController {
	
	 // TODO: Inject Bid service

    @Autowired
    private BidListService bidListService;
    
	@Autowired
	private UserService userService;
    

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) throws Exception {
		model.addAttribute("bidList", bidListService.getAllBidList());
    	User userPrincipal = userService.getUserByUsername(principal.getName());
    	String principalUser = "";
    	if(userPrincipal != null) {
    		principalUser = userPrincipal.getRole();
    	}else principalUser = "USER";
    	
		model.addAttribute("principal",principalUser);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
    	
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model, RedirectAttributes redirect){
        // TODO: check data valid and save to db, after saving return bid list
    	if(!result.hasErrors()) {
	    	try {
				bidListService.saveBidList(bidList);
				model.addAttribute("bidList", bidListService.getAllBidList());
				redirect.addFlashAttribute("messageSuccess", "Bid added successfully !");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
				return "redirect:/bidList/add";
			}
    	}
    	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: get Bid by Id and to model then show to the form
		try {
			BidList bid = bidListService.getbidListById(id);
			model.addAttribute("bidList", bid);
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model, RedirectAttributes redirect) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
    	if (result.hasErrors()) {
            return "bidList/update";
        }
    	try {
			bidListService.updateBidList(bidList);
	    	model.addAttribute("bidList", bidListService.getAllBidList());
	    	redirect.addFlashAttribute("messageSuccess", "BidList was update by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
    	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
    	try {
    		BidList bidList = bidListService.getbidListById(id);
			bidListService.deleteBidList(bidList);
			model.addAttribute("bidList", bidListService.getAllBidList());
			redirect.addFlashAttribute("messageSuccess", "BidList was delete by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "redirect:/bidList/list";
    }

}
