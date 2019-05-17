package com.revature.menus;
//package com.revature.CarDealership.factory;

import com.revature.menus.CustomerMenu;
import com.revature.menus.EmployeeMenu;
import com.revature.menus.Menu;
import com.revature.menus.SystemMenu;
import com.revature.menus.UserMenu;
import com.revature.enums.UserType;

public class MenuFactory {

	private MenuFactory() {
		
	}
	
	public static Menu getMenu(UserType user) {
		if (user == UserType.CUSTOMER) {
			return new CustomerMenu();
		}
		
		if (user == UserType.EMPLOYEE) {
			return new EmployeeMenu();
		}
		
		if (user == UserType.USER) {
			return new UserMenu();
		}
		
		if (user == UserType.SYSTEM) {
			return new SystemMenu();
		}
		
		return null;
		
	}
	
}
