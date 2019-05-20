package com.revature.driver;

import org.apache.log4j.Logger;

import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.MenuFactory;

public class Driver {
	private static Logger log = Logger.getLogger(Driver.class);

	public static void main(String[] args) {

		UserType user = UserType.USER;
		Menu menu = MenuFactory.getMenu(user);

		while (true) {
			log.info(menu.getClass());
			menu = menu.runMenu();
		}
	}
}