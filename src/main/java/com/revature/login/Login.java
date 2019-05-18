package com.revature.login;

import org.apache.log4j.Logger;

import com.revature.login.Validate;
import com.revature.menus.Menu;
import com.revature.menus.MenuFactory;
import com.revature.utils.DBConnection;
import com.revature.beans.Account;
import com.revature.enums.UserType;
import com.revature.login.Login;

public class Login extends AccountInfo {
	
	private static Logger log = Logger.getLogger(Login.class);
	private static DBConnection connection = DBConnection.getDBConnection();
	
//	final static Logger logger = Logger.getLogger(Login.class);

	public Account signIn() {
		username = retrieveUserInputUsername();
		password = retrieveUserInputPassword();
//		accountType = retrieveUserSelectionForAccountType();
		Account account = new Account(username, password);
		
		return signIn(account);
	}

	public Account signIn(Account account) {
		Account existingAccount = isAccountExistant(account.getUsername(), account.getPassword());
		
		if (null != existingAccount) {
			
			if (existingAccount.getPassword().equals(account.getPassword())) {
				return existingAccount;
			}
		}
		
		return null;
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
	

}
