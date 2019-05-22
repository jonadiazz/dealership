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

	public static Menu getMenu(String user) {
		if (user.equals(UserType.CUSTOMER.toString())) {
			return new CustomerMenu();

		}

		if (user.equals(UserType.EMPLOYEE.toString())) {
			return new EmployeeMenu();

		}

		if (user.equals(UserType.USER.toString())) {
			return new UserMenu();

		}

		if (user.equals(UserType.SYSTEM.toString())) {
			return new SystemMenu();

		}

		return null;

	}

	public static Menu getMenu(UserType userType) {
		return getMenu(userType.toString());

	}

}
