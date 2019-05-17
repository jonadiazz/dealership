package com.revature.menus;

import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;

public class UserMenu extends Menu {
    String[] optionsAsUser = {"Login", "Register for customer account"};

	@Override
	public void showOptions() {
		printMenu(optionsAsUser);
		// TODO Auto-generated method stub

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsUser.length);

		// TODO Auto-generated method stub
	}

}