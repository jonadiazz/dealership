package com.revature.menus;

import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;

public class EmployeeMenu extends Menu {

    String [] optionsAsEmployee = 
    	{"Add a car",  "Accept or reject offer for a car", "Remove a car", "View all payments", };

	@Override
	public void showOptions() {
		printMenu(optionsAsEmployee);
		// TODO Auto-generated method stub

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsEmployee.length);

		// TODO Auto-generated method stub
	}

}
