package com.nnk.springboot.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

@SpringBootTest
public class UserServiceUnitTest {

	@MockBean
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	User user;
	
	@BeforeEach
	void setUp() {
		
		user = new User("Mohammedbenab", "Hello@75", "Mohammed Benabdallah", "User");
		
	}
	
	@Test
	void save() throws UserNotFoundException, InvalidPasswordException {
		Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
		User userCreated = userService.saveUser(user);
		assertEquals(user.getUsername(), userCreated.getUsername());
		verify(userRepository, Mockito.times(1)).save(user);
	}
	
	@Test
	void update() throws UserNotFoundException {
		user.setId(1);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		user.setUsername("MohammedBenben");
		userService.updateUser(user);
		verify(userRepository, Mockito.times(1)).save(user);
		verify(userRepository, Mockito.times(1)).findById(1);
		
	}
	
	@Test
	void delete() throws UserNotFoundException {
		user.setId(1);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		userService.deleteUser(user);
		verify(userRepository, Mockito.times(1)).delete(user);
		
	}
	
	@Test
	void getById() throws UserNotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		User userGetById = userService.getUserById(1);
		verify(userRepository, Mockito.times(1)).findById(1);
		assertEquals(userGetById, user);
	}
	
	@Test
	void getAll() {
		List<User> list = new ArrayList<>();
		list.add(user);
		Mockito.when(userRepository.findAll()).thenReturn((list));
		List<User> listFound = userService.getAllUsers();
		verify(userRepository, Mockito.times(1)).findAll();
		assertEquals(list, listFound);
		
	}
}
