package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.InvalidPasswordException;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.UserService;

@SpringBootTest
public class UserTest {
	
	
	@Autowired
	private UserRepository userRepository;
//	
//	@Autowired
	private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//	
	@Autowired
	private UserService userService;
		
	private User user;
	
	@AfterEach
	void clean() {
		this.userRepository.deleteAll();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User("Mohammedbenab", "Hello@75", "Mohammed Benabdallah", "ADMIN");
//		User user2 = new User
	}
	
	@Test
	void succesCheckPassword() throws Exception {
		String password = "s7az%$8DA";
		assertTrue(userService.passwordMatch(password));
	}
//
	@Test
	void whenUserIsSaved() throws Exception {
		userService.saveUser(user);
		assertEquals(1, userRepository.findAll().size());
	}
	
	@Test
	void saveWhenUserExist() throws Exception {
		userService.saveUser(user);
		assertThrows(UserNotFoundException.class, ()->{
			userService.saveUser(user);
		});
	}
	
	@Test
	void whenUserNotExistButPasswordNotCorrect() throws Exception {
		user.setPassword("Hello World");
		assertThrows(InvalidPasswordException.class, ()->{
			userService.saveUser(user);
		});
	}
	
	@Test
	void updateUser() throws Exception {
		userService.saveUser(user);
		User userDb = userRepository.getByUsername(user.getUsername());
		String usernameBefore = userDb.getUsername();
		System.out.println("username before : "+usernameBefore);
		userDb.setUsername("Mohammed el Amine");
		userDb = userService.updateUser(userDb);
		String usernameAfter = userDb.getUsername();
		System.out.println("username after : "+usernameAfter);
		assertEquals(1, userRepository.findAll().size());
		assertEquals("Mohammedbenab", usernameBefore);
		assertEquals("Mohammed el Amine", usernameAfter);
	}
	
	@Test
	void deleteUser() throws Exception {
		userService.saveUser(user);
		User userDb = userService.getUserByUsername(user.getUsername());
		userService.deleteUser(userDb);
		assertNotNull(userDb);
		assertEquals(0, userRepository.findAll().size());
	}
//	
	@Test
	void getUserList() throws Exception {
		userService.saveUser(user);
		User user2 = new User("Sara", "Savoir-Faire8@", "Sara Laroche", "USER");
		userService.saveUser(user2);
		List<User> list = userService.getAllUsers();
		assertNotNull(list);
		assertEquals(2, userRepository.findAll().size());
	}
//	
	@Test
	void updatePassword() throws Exception {
		userService.saveUser(user);
		User user = userService.getUserByUsername("Mohammedbenab");
		String before = user.getPassword();
		System.out.println(before);
		user.setPassword("Hello@World7");
		userService.updatePassword(user);
		User userAfterUpdatePass = userService.getUserByUsername("Mohammedbenab");
		String passAfter = userAfterUpdatePass.getPassword();

		assertEquals(1, userRepository.findAll().size());
		assertEquals("Hello@75", before);
		assertTrue(bCryptPasswordEncoder.matches("Hello@World7", passAfter));

	}

	@Test
	void getUserByUsername() throws Exception {
		userService.saveUser(user);
		User userDb = userService.getUserByUsername(user.getUsername());
		assertNotNull(userDb);
		assertEquals(1, userRepository.findAll().size());
	}
	
	@Test
	void getUserByUserId() throws Exception {
		userService.saveUser(user);
		User userDb = userRepository.findAll().get(0);
		User userById = userService.getUserById(userDb.getId());
		assertNotNull(userDb);
		assertEquals(1, userRepository.findAll().size());
		assertEquals(userDb.getId(), userById.getId());
	}

}
