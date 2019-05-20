package com.revature.menus;

import java.util.ArrayList;
import java.util.List;

import com.revature.enums.UserType;

public class RegisterMenu extends Menu {
	List<String> optionsAsRegisterUser = new ArrayList<String>();

	@Override
	public void showOptions() {
		System.out.println("Select account type");

		for (UserType userType : UserType.values()) {
			String ut = userType.toString();
			optionsAsRegisterUser.add(ut);
		}

		printMenu(optionsAsRegisterUser);

	}

	@Override
	public int retrieveUserInputOption() {
		int length = optionsAsRegisterUser.size();
		int option = ValidateUserSelectedOption.getInstance().validateOption(length);

		return option;

	}

	@Override
	public Menu runMenu() {
		return null;

	}

}
