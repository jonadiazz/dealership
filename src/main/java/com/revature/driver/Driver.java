package com.revature.driver;

import org.apache.log4j.Logger;

import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.MenuFactory;
import com.revature.session.Session;

public class Driver {
	private static Logger log = Logger.getLogger(Driver.class);

	public static void main(String[] args) {

		UserType user = UserType.USER;
		Menu menu = MenuFactory.getMenu(user);

		while (true) {
			log.trace(menu.getClass());
			
			System.out.printf("\nSession info: Session ID is " + Session.ID + " for " + Session.Usertype.toLowerCase() + " " + Session.Username +"\n");
			
			menu = menu.runMenu();
			
		}
	}
}