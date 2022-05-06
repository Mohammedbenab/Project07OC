package com.nnk.springboot.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.LowerCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.service.UserService;

@Controller
public class CurvePointController {

	 // TODO: Inject Curve Point service
	
		@Autowired
		private CurvePointService curvePointService;
		
		@Autowired
		private UserService userService;

	    @RequestMapping("/curvePoint/list")
	    public String home(Model model, Principal principal) throws Exception
	    {
	        // TODO: find all Curve Point, add to model
	    	model.addAttribute("curvePoint", curvePointService.getAllCurvePoint());
	    	User userPrincipal = userService.getUserByUsername(principal.getName());
	    	String principalUser = "";
	    	if(userPrincipal != null) {
	    		principalUser = userPrincipal.getRole();
	    	}else principalUser = "USER";
			model.addAttribute("principal",principalUser);
	        return "curvePoint/list";
	    }

	    @GetMapping("/curvePoint/add")
	    public String addBidForm(CurvePoint curvePoint) {
	    	
	        return "curvePoint/add";
	    }

	    @PostMapping("/curvePoint/validate")
	    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model, RedirectAttributes redirect){
	        // TODO: check data valid and save to db, after saving return Curve list
	    	if(!result.hasErrors()) {
				try {
					curvePointService.saveCurvePoint(curvePoint);
					model.addAttribute("curvePoint", curvePointService.getAllCurvePoint());
					redirect.addFlashAttribute("messageSuccess", "Bid added successfully !");
				} catch (Exception e) {
					redirect.addFlashAttribute("messageError", e.getMessage());
					return "redirect:/curvePoint/add";
				}
	    	}
			return "redirect:/curvePoint/list";
	    }

	    @GetMapping("/curvePoint/update/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
	        // TODO: get CurvePoint by Id and to model then show to the form
	    	CurvePoint curvePoint = curvePointService.getCurvePointById(id);
	    	model.addAttribute("curvePoint", curvePoint);
	        return "curvePoint/update";
	    }

	    @PostMapping("/curvePoint/update/{id}")
	    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
	                             BindingResult result, Model model, RedirectAttributes redirect) {
	        // TODO: check required fields, if valid call service to update Curve and return Curve list
	    	if (result.hasErrors()) {
	            return "curvePoint/update";
	        }
	    	try {
	    		curvePointService.updateCurvePoint(curvePoint);
		    	model.addAttribute("curvePoint", curvePointService.getAllCurvePoint());
		    	redirect.addFlashAttribute("messageSuccess", "CurvePoint was update successfully");
		    	
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
		
			}
	        return "redirect:/curvePoint/list";
	    }

	    @GetMapping("/curvePoint/delete/{id}")
	    public String deleteBid(@PathVariable("id") Integer id) throws CurvePointNotFoundException {
	        // TODO: Find Curve by Id and delete the Curve, return to Curve list
	    			CurvePoint curvePoint = curvePointService.getCurvePointById(id);
	    			curvePointService.deleteCurvePoint(curvePoint);

	        return "redirect:/curvePoint/list";
	    }
}
