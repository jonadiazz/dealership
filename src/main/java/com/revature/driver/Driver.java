package com.revature.driver;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.enums.UserType;
import com.revature.menus.MenuFactory;
import com.revature.menus.UserMenu;
import com.revature.login.Login;
import com.revature.login.RegisterAccount;
import com.revature.menus.Menu;

public class Driver {
	private static Logger log = Logger.getLogger(Driver.class);

    public static void main( String[] args ) {
    	
    	UserType user = UserType.USER;
    	Menu menu = MenuFactory.getMenu(user);

    	menu.runMenu();
//    	mainLoop: while(true) {
//    		if (menu instanceof UserMenu) {
//    			
//    		}
//    	}
    }
}