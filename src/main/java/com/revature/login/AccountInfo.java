package com.revature.login;

public abstract class AccountInfo {

	public abstract String retrieveUserInputUsername();

	public abstract String retrieveUserInputPassword();

	// TODO: Move variables to classes implementing this class
	public String username;
	public String password;
	public String accountType;

}
