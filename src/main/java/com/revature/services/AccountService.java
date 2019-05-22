package com.revature.services;

import com.revature.beans.Account;

public interface AccountService {
	public void addAccount(Account account);

	public Account getAccount(String username, String password);
	
	public String getCustomerUsernameById(Integer customerId);

}
