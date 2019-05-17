package com.revature.menus;

@SuppressWarnings("serial")
public class NonOptionException extends Exception {
	public NonOptionException(String exceptionMessage) {
		super("Invalid option. Try again");
	}
}
