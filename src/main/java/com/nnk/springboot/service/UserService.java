package com.nnk.springboot.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.repository.UserRepository;

@Service
public class UserService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
	
	public static final String Password_Regex = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[\\$\\%\\#\\@]).{8,})";//(?=.*[$%@#])
	
	private Pattern pattern;
	private Matcher matcher;
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public PasswordEncoder  bcyptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserNotFoundException
	 * @throws InvalidPasswordException
	 */
	public User saveUser(User user) throws UserNotFoundException, InvalidPasswordException {
		User user1 = userRepository.findByUsername(user.getUsername());
		if (user1 == null ) {
			if(passwordMatch(user.getPassword())){
			User userNew = new User();
			userNew.setUsername(user.getUsername());
			userNew.setFullname(user.getFullname());
			userNew.setRole(user.getRole());
			userNew.setPassword(bcyptPasswordEncoder().encode(user.getPassword()));
			userRepository.save(userNew);
			logger.info("User "+ userNew.getUsername() +" saved successfully");
			return userNew;
			}else {
				throw new InvalidPasswordException("Password not conform to security rules");
			}
		}else {
			logger.error("User " +user.getUsername() + " hasn't been saved");
			throw new UserNotFoundException("User "+ user.getUsername()+ " already exist !");
		}		
	}

	/**
	 * 
	 * @param user
	 * @throws UserNotFoundException
	 */
	public void deleteUser(User user) throws UserNotFoundException {
		User user1 = userRepository.findById(user.getId()).orElse(null);
		if (user1 != null) {
			userRepository.delete(user1);
			logger.info("User "+user1.getUsername()+ " has been deleted successfully");
		}else {
			logger.error("User " +user.getUsername() + " hasn't been deleted");
			throw new UserNotFoundException("User "+ user.getUsername()+ " not be deleted !");
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserNotFoundException
	 */
	public User updateUser(User user) throws UserNotFoundException {
		User user1 = userRepository.findById(user.getId()).orElse(null);
		if (user1 != null) {
			user1.setFullname(user.getFullname());
			user1.setUsername(user.getUsername());
			user1.setRole(user.getRole());
			userRepository.save(user1);
			logger.info("User "+user1.getUsername()+ " has been update successfully");
			return user1;
		}else {
			logger.error("User " +user.getUsername() + " hasn't been updated");
			throw new UserNotFoundException("User "+ user.getUsername()+ " not be updated !");
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws InvalidPasswordException
	 */
	public User updatePassword(User user) throws InvalidPasswordException {
		User user1 = userRepository.findById(user.getId()).orElse(null);
		if(user1 != null && passwordMatch(user.getPassword())) {
			user1.setPassword(bcyptPasswordEncoder().encode(user.getPassword()));
			userRepository.save(user1);
			logger.info("Password of "+user1.getUsername()+" updated");
			return user1;
		}else {
			logger.error("Password of "+user1.getUsername()+ " hasnt update");
			throw new InvalidPasswordException("Password of "+ user1.getUsername()+" not conform to security rules");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) {
		return userRepository.findById(id).get();
		 
	}
	
	/**
	 * 
	 * @return List of all users
	 */
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username) {
		return userRepository.getByUsername(username);
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public boolean passwordMatch(String password) {
		pattern = Pattern.compile(Password_Regex);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
