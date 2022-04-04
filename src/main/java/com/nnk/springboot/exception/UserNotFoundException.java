package com.nnk.springboot.exception;

public class UserNotFoundException extends Exception{
	
	public UserNotFoundException (String message) {
		super(message);
	}

}
