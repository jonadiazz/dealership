package com.revature.login;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceOracle;

public class RegisterAccount extends AccountInfo {

	private static Logger log = Logger.getLogger(Login.class);

	public Account register() {
		AccountService as = new AccountServiceOracle();

		username = retrieveUserInputUsername();
		password = retrieveUserInputPassword();

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

	}

	@Override
	public String retrieveUserInputPassword() {
		return Validate.getInstance().validatePassword();
	}

}
