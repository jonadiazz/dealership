package com.revature.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.MenuFactory;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceOracle;
import com.revature.utils.DBConnection;

public class RegisterAccount extends AccountInfo {

	private static Logger log = Logger.getLogger(Login.class);
	private static DBConnection connection = DBConnection.getDBConnection();
	
	public Account register() {
		AccountService as = new AccountServiceOracle();

		username = retrieveUserInputUsername();
		password = retrieveUserInputPassword();
//		accountType = retrieveUserSelectionForAccountType();
		
		if (as.getAccount(username, password) != null) {
			log.trace("Account already registered, try to log in instead!");
			
			return null;
		}
		
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		
		as.addAccount(account);
		
		return account;
		
	}

	@Override
	public String retrieveUserInputUsername() {
		return Validate.getInstance().validateUsername();
		// TODO Auto-generated method stub
		
	}

	@Override
	public String retrieveUserInputPassword() {
		return Validate.getInstance().validatePassword();

		// TODO Auto-generated method stub
		
	}
	
	private String retrieveUserSelectionForAccountType() {
		Menu accountTypeMenu = MenuFactory.getMenu(UserType.REGISTER);
		accountTypeMenu.showOptions();
		
		List<String> options = new ArrayList<String>();
		
		for (UserType userType: UserType.values()) {
			String ut = userType.toString();
			options.add(ut);
		}
		// TODO Auto-generated method stub
		int option = accountTypeMenu.retrieveUserInputOption();
		
		return options.get(option);
	}



}
