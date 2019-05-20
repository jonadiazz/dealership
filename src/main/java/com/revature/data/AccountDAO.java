package com.revature.data;

import com.revature.beans.Account;

public interface AccountDAO {

	int addAccount(Account account);

	Account getAccount(String username, String password);
}
