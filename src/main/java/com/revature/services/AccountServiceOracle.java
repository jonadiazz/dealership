package com.revature.services;

import com.revature.beans.Account;
import com.revature.data.AccountDAO;
import com.revature.data.AccountOracle;

public class AccountServiceOracle implements AccountService {

	private AccountDAO accountDAO = new AccountOracle();

	@Override
	public void addAccount(Account account) {
		accountDAO.addAccount(account);
	}

	@Override
	public Account getAccount(String username, String password) {
		return accountDAO.getAccount(username, password);
	}

	@Override
	public String getCustomerUsernameById(Integer customerId) {
		return accountDAO.getCustomerUsernameById(customerId);
	}

}
