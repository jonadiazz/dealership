package com.revature.beans;

public class Account {

	private String username;
	private String password;
	private String accountType;

	private int id;

	public Account() {
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;

	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String userType) {
		this.accountType = userType.toString();
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int key) {
		this.id = key;
	}

	public Integer getId() {
		return this.id;
	}

}
