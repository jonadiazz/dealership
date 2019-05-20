package com.revature.login;

import com.revature.beans.Account;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceOracle;
import com.revature.session.Session;

public class Login extends AccountInfo {

	public Account signIn() {
		username = retrieveUserInputUsername();
		password = retrieveUserInputPassword();
		Account account = new Account(username, password);

		return signIn(account);
	}

	public Account signIn(Account account) {
		AccountService as = new AccountServiceOracle();

		Account existingAccount = as.getAccount(account.getUsername(), account.getPassword());

		if (null != existingAccount) {

			if (existingAccount.getPassword().equals(account.getPassword())) {
				Session.ID = existingAccount.getId().toString();
				return existingAccount;
			}
		}

		return null;
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
