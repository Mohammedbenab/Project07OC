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
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.RuleService;
import com.nnk.springboot.service.UserService;

@Controller
public class RuleController {
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private UserService userService;
    

    @RequestMapping("/rule/list")
    public String home(Model model, Principal principal) throws Exception {
		model.addAttribute("ruleList", ruleService.getList());
    	User userPrincipal = userService.getUserByUsername(principal.getName());
    	String principalUser = "";
    	if(userPrincipal != null) {
    		principalUser = userPrincipal.getRole();
    	}else principalUser = "USER";
    	
		model.addAttribute("principal",principalUser);
        return "rule/list";
    }

    @GetMapping("/rule/add")
    public String addRuleForm(Rule rule) {
    	
        return "rule/add";
    }

    @PostMapping("/rule/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model, RedirectAttributes redirect){
        // TODO: check data valid and save to db, after saving return rating list
    	if(!result.hasErrors()) {
	    	try {
				ruleService.add(rule);
				model.addAttribute("ruleList", ruleService.getList());
				redirect.addFlashAttribute("messageSuccess", "Rating added successfully !");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
				return "redirect:/rule/add";
			}
    	}
    	return "redirect:/rule/list";
    }

    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: get rating by Id and to model then show to the form
		try {
			Rule rule = ruleService.getById(id);
			model.addAttribute("rule", rule);
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "rule/update";
    }

    @PostMapping("/rule/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model, RedirectAttributes redirect) {
        // TODO: check required fields, if valid call service to update rating and return list rating
    	if (result.hasErrors()) {
            return "rule/update";
        }
    	try {
    		ruleService.update(rule);
	    	model.addAttribute("ratingList", ruleService.getList());
	    	redirect.addFlashAttribute("messageSuccess", "Rating was update by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
    	return "redirect:/rule/list";
    }

    @GetMapping("/rule/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        // TODO: Find rating by Id and delete the bid, return to Bid list
    	try {
    		Rule rule = ruleService.getById(id);
    		ruleService.delete(rule);
			model.addAttribute("rating", ruleService.getList());
			redirect.addFlashAttribute("messageSuccess", "Rating was delete by success");
		} catch (Exception e) {
			redirect.addFlashAttribute("messageError", e.getMessage());
		}
        return "redirect:/rule/list";
    }

}
