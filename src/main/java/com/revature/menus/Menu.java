package com.revature.menus;

import java.util.List;

public abstract class Menu {

	public abstract void showOptions();

	public abstract int retrieveUserInputOption();

	public void printMenu(String[] options) {
		System.out.println();

		for (int i = 0; i < options.length; i++) {
			System.out.printf("[%d] %s\n", i + 1, options[i]);
		}

		System.out.println();
	}

	public void printMenu(List<String> options) {
		String[] tmp = new String[options.size()];

		for (int i = 0; i < options.size(); i++) {
			tmp[i] = options.get(i);
		}

		printMenu(tmp);
	}

	public abstract Menu runMenu();

}