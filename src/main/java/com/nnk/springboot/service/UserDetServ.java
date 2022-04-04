package com.nnk.springboot.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;

@Service
public class UserDetServ implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userService.getUserByUsername(username);
		System.out.println(user.getUsername());
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
				);
//		 Customer myUser = customerService.getCustomerByName(username);
//		 System.out.println(myUser.getName());
//	        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(myUser.getName())
//	                .password(myUser.getPassword())
//	                .authorities(getAuthorities(myUser)).build();
//
//	        return user;

	    }

//	    private Collection<GrantedAuthority> getAuthorities(Customer myUser) {
//	        Collection<GrantedAuthority> authorities = new ArrayList<>(2);
//	        if (myUser.getRole().equals("ADMIN")) {
//	            authorities.add(new SimpleGrantedAuthority("ADMIN"));
//	        } else if (myUser.getRole().equals("USER")) {
//	            authorities.add(new SimpleGrantedAuthority("USER"));
//	        }
//	        return authorities;
//	    }

}
