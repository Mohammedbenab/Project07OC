package com.nnk.springboot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;
//
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    private User admin;
    
    @AfterEach
    void deleteUser() {
    	userRepository.deleteAll();
    }
    
    @BeforeEach
    void setUp() throws UserNotFoundException, InvalidPasswordException {
    	admin = new User("Mohammedbenab", "Hello@75", "Mohammed Benabdallah", "ADMIN");
    	userService.saveUser(admin);
    }
    
    @Test
     void testListUserController() throws Exception{
    	
    	// List of Users
    	mockMvc
    	.perform(get("/user/list")
   			 .with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN"))))
   			.andExpect(status().isOk())
   			.andExpect(view().name("user/list"));
    }
    
    @Test
    void testUserForm() throws Exception {
    	// Form add
    	mockMvc
    			.perform(get("/user/add")
    			 .with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN"))))
    			.andExpect(status().isOk())
    			.andExpect(view().name("user/add"));
    }
    
    @Test
    void testAddUser() throws Exception {
    	//Save user
    	mockMvc
		    	.perform(post("/user/validate")
			   		.with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN")))
			   		.param("username","username")
                    .param("password","Hello@75")
                    .param("fullname","full name")
                    .param("role","USER")
                    .with(csrf()))
		    		.andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    void testUserUpdateForm() throws Exception {
    	User user = userService.getUserByUsername("Mohammedbenab");
    	
//    	//Form update
    	mockMvc
				.perform(get("/user/update/{id}", user.getId())
				.with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN"))))
				.andExpect(status().isOk())
				.andExpect(view().name("user/update"));
				
    }
    
    @Test
    void testUpdateUser() throws Exception {
    	User user = userService.getUserByUsername("Mohammedbenab");
    	
    	//Update user
    	mockMvc
    			.perform(post("/user/update/{id}",user.getId())
    			.with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN")))
		   		.param("username","Momo")
                .param("password","Hello@75")
                .param("fullname","full name")
                .param("role","USER")
                .with(csrf()))
	    		.andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    void testDeleteUser() throws Exception{
    	User user = userService.getUserByUsername("Mohammedbenab");
    	
    	//Delete user
    	mockMvc
				.perform(get("/user/delete/{id}",user.getId())
				.with(user("admin").roles("ADMIN").authorities(new SimpleGrantedAuthority("ADMIN")))
		        .with(csrf()))
				.andExpect(redirectedUrl("/user/list"));
		    }
        
}
