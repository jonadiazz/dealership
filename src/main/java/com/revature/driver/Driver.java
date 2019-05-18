package com.revature.driver;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.enums.UserType;
import com.revature.menus.MenuFactory;
import com.revature.login.Login;
import com.revature.login.RegisterAccount;
import com.revature.menus.Menu;

public class Driver
{
	private static Logger log = Logger.getLogger(Driver.class);

    public static void main( String[] args )
    {
    	
    	Menu userMenu = MenuFactory.getMenu(UserType.USER);

    	Account account = null;
		Login login = new Login();
		RegisterAccount registerAccount = new RegisterAccount();
		
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
    	
    	
		start: { 
			while (true) {
		
				userMenu.showOptions();
				int pickedOption = userMenu.retrieveUserInputOption();
    	
				switchLabel: {
					switch(pickedOption) {
    			
    					case 1:
    						account = login.signIn();
    						log.info("Signing in");

    						if (account != null) {
    							log.trace(account.getAccountType());
    							Menu menu = MenuFactory.getMenu(account.getAccountType());

    							subMenu: { 
    								while(true) {
    									menu.showOptions();
    									pickedOption = menu.retrieveUserInputOption();
    									if (pickedOption == 5) {
    										log.info("Logging out");
    										break switchLabel;
    									}
    								}
    							}
    						} else {
    							log.info("Failed to log in!");
    							continue;
    						}
        			
//    						break;
        			
    					case 2:
        			
    						Account accountCreated = registerAccount.register();
        			
    						if (accountCreated != null) {
    							account = login.signIn(accountCreated);
    							log.info("Account type: " + account.getAccountType());
    							Menu menu = MenuFactory.getMenu(account.getAccountType());
    							menu.showOptions();
    							pickedOption = menu.retrieveUserInputOption();
//    							break menuSelection;
    						}
        			
    						break;
        			
    					case 4:
    						System.exit(1);
    				}
    			} //switchLabel
			}
		} // start
    }
}
