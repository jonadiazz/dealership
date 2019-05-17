package com.revature.menus;

public abstract class Menu {
	
	public abstract void showOptions();
	public abstract int retrieveUserInputOption();
	
	public void printMenu(String[] options) {
		System.out.println();
		
		for(int i = 0; i < options.length; i++) {
			System.out.printf("%d. %s\n", i+1, options[i]);
		}
		
		System.out.println();
	}


}