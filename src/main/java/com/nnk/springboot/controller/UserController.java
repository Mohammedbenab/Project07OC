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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user/list")
	public String userList(Model model, Principal principal) {
		model.addAttribute("users", userService.getAllUsers());
    	User userPrincipal = userService.getUserByUsername(principal.getName());
    	String principalUser = "";
    	if(userPrincipal != null) {
    		principalUser = userPrincipal.getRole();
    	}else principalUser = "USER";
		model.addAttribute("principal",principalUser);
		return "user/list";
	}
	
	@GetMapping("/user/add")
	 public String addUser(User user) {
        return "user/add";
    }
	
	
	 @PostMapping("/user/validate")
	    public String validate(@Valid User user, BindingResult result, Model model, RedirectAttributes redirect){
	    	if (!result.hasErrors()) {
	            	try {
						userService.saveUser(user);
						model.addAttribute("users", userService.getAllUsers());
						redirect.addFlashAttribute("messageSuccess", "User added successfully !");
					} catch (Exception e) {
						redirect.addFlashAttribute("messageError", e.getMessage());
						return "redirect:/user/add";
					}		
	    	}
	            return "redirect:/user/list";
	    }

	    @GetMapping("/user/update/{id}")
	    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {

			try {
				User user = userService.getUserById(id);
		        user.setPassword("");
		        model.addAttribute("user", user);
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
			}
	        return "user/update";
	    }

	    @PostMapping("/user/update/{id}")
	    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
	                             BindingResult result, Model model, RedirectAttributes redirect){
	        if (result.hasErrors()) {
	            return "user/update";
	        }
	        try {
				userService.updateUser(user);
				model.addAttribute("users", userService.getAllUsers());
				redirect.addFlashAttribute("messageSuccess", "User was update successfully");
			} catch (Exception e) {
				redirect.addFlashAttribute("messageError", e.getMessage());
			}
	        return "redirect:/user/list";
	    }

	    @GetMapping("/user/delete/{id}")
	    public String deleteUser(@PathVariable("id") Integer id) throws UserNotFoundException{
        	User user = userService.getUserById(id);
			userService.deleteUser(user);
	        return "redirect:/user/list";

//	        try {
//	        	User user = userService.getUserById(id);
//				userService.deleteUser(user);
//				model.addAttribute("users", userService.getAllUsers());
//				redirect.addFlashAttribute("messageSuccess","User was deleted !");
//			} catch (Exception e) {
//				redirect.addFlashAttribute("messageError", e.getMessage());
//			}
//	        return "redirect:/user/list";
	    }
}
