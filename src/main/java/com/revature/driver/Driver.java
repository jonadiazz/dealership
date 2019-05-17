package com.revature.driver;

import com.revature.enums.UserType;
import com.revature.menus.MenuFactory;
import com.revature.login.Login;
import com.revature.menus.Menu;

public class Driver
{
    public static void main( String[] args )
    {
    	Menu userMenu = MenuFactory.getMenu(UserType.USER);

		Login login = new Login();

    	/** Menus:
    		Menu customerMenu = MenuFactory.getMenu(UserType.CUSTOMER);
    		Menu employeeMenu = MenuFactory.getMenu(UserType.EMPLOYEE);
    		Menu systemMenu = MenuFactory.getMenu(UserType.SYSTEM);
    		Menu userMenu = MenuFactory.getMenu(UserType.USER);
    	
    		customerMenu.showOptions();
    		employeeMenu.showOptions();
    		systemMenu.showOptions();
    		userMenu.showOptions();
    	**/
    	
    	
    	userMenu.showOptions();
    	int pickedOption = userMenu.retrieveUserInputOption();
    	
    	if (pickedOption == 1) {
    		login.retrieveUserInputUsername();
    		login.retrieveUserInputPassword();
    		if (login.signIn()) {
    			Menu customerMenu = MenuFactory.getMenu(UserType.CUSTOMER);
    			customerMenu.showOptions();
    		}
    	}
    
//    	if ()
    	
    }
}
