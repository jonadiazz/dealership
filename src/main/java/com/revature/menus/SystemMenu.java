package com.revature.menus;

import com.revature.menus.ValidateUserSelectedOption;
import com.revature.menus.Menu;

public class SystemMenu extends Menu {

	String[] optionsAsSystem = { "reject all other pending offers", "calculate monthly payment" };

	@Override
	public void showOptions() {
		printMenu(optionsAsSystem);

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsSystem.length);

	}

	@Override
	public Menu runMenu() {
		return null;

	}

}