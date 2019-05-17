package com.revature.menus;

import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;

public class CustomerMenu extends Menu {

    private String[] optionsAsCustomer = 
    	{"Make an offer for a car", "View cars", "View cars I own", "View remaining payments"};

	@Override
	public void showOptions() {
		printMenu(this.optionsAsCustomer);
		// TODO Auto-generated method stub
		
	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsCustomer.length);
		// TODO Auto-generated method stub
	}
}