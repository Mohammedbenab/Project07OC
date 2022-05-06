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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.service.UserService;

@Controller
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private UserService userService;
	
	
	 @GetMapping("/trade/list")
	    public String home(Model model, Principal principal) throws Exception {
			model.addAttribute("tradeList", tradeService.getList());
	    	User userPrincipal = userService.getUserByUsername(principal.getName());
	    	String principalUser = "";
	    	if(userPrincipal != null) {
	    		principalUser = userPrincipal.getRole();
	    	}else principalUser = "USER";
	    	
			model.addAttribute("principal",principalUser);
	        return "trade/list";
	    }

	    @GetMapping("/trade/add")
	    public String addRuleForm(Trade trade) {
	    	
	        return "trade/add";
	    }

	    @PostMapping("/trade/validate")
	    public String validate(@Valid Trade trade, BindingResult result, Model model, RedirectAttributes redirect){
	        // TODO: check data valid and save to db, after saving return rating list
	    	if(!result.hasErrors()) {
		    	try {
					tradeService.add(trade);
					model.addAttribute("tradeList", tradeService.getList());
					redirect.addFlashAttribute("messageSuccess", "Trade added successfully !");
				} catch (Exception e) {
					redirect.addFlashAttribute("messageError", e.getMessage());
					return "redirect:/trade/add";
				}
	    	}
	    	return "redirect:/trade/list";
	    }
	    
	    @GetMapping("/trade/update/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
	        // TODO: get rating by Id and to model then show to the form
			try {
				Trade trade = tradeService.getById(id);
				model.addAttribute("trade", trade);
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
			}
	        return "trade/update";
	    }

	    @PostMapping("/trade/update/{id}")
	    public String updateRating(@PathVariable("id") Integer id, @Valid Trade trade,
	                             BindingResult result, Model model, RedirectAttributes redirect) {
	        // TODO: check required fields, if valid call service to update rating and return list rating
	    	if (result.hasErrors()) {
	            return "trade/update";
	        }
	    	try {
//	    		trade.setTradeId(id);
	    		tradeService.update(trade);
		    	model.addAttribute("tradeList", tradeService.getList());
		    	redirect.addFlashAttribute("messageSuccess", "Trade was update by success");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
			}
	    	return "redirect:/trade/list";
	    }

	    @GetMapping("/trade/delete/{id}")
	    public String deleteRule(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
	        // TODO: Find rating by Id and delete the bid, return to Bid list
	    	try {
	    		Trade trade = tradeService.getById(id);
	    		tradeService.delete(trade);
				//model.addAttribute("tradeList", tradeService.getList());
				redirect.addFlashAttribute("messageSuccess", "Trade was delete by success");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
			}
	        return "redirect:/trade/list";
	    }


}
