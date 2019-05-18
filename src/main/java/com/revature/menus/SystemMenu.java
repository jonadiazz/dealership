package com.revature.menus;

import com.revature.menus.ValidateUserSelectedOption;
import com.revature.menus.Menu;

public class SystemMenu extends Menu {

    String[] optionsAsSystem = {"reject all other pending offers", "calculate monthly payment"};

	@Override
	public void showOptions() {
		printMenu(optionsAsSystem);
		// TODO Auto-generated method stub
	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsSystem.length);
		// TODO Auto-generated method stub
	}

	@Override
	public Menu runMenu() {
		// TODO Auto-generated method stub
		return null;
	}

}