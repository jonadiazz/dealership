package com.revature.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.utils.DBConnection;
import com.revature.utils.LogUtil;

import oracle.jdbc.logging.annotations.Log;

public abstract class AccountInfo {
	

	public abstract String retrieveUserInputUsername();
	public abstract String retrieveUserInputPassword();
	
	public String username;
	public String password;
	public String accountType;

	private static Logger log = Logger.getLogger(AccountInfo.class);
	private static DBConnection connection = DBConnection.getDBConnection();

	
	public Account isAccountExistant(String username, String password) {
		String sql = "select * from Accounts";
		
		try (Connection conn = connection.getConnection()) {
//			log.trace(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				if (username.equals(rs.getString("username"))) {
					Account account;
					
					account = new Account(username, rs.getString("password"));
					account.setAccountType(rs.getString("account_type"));
					
					return account;
				}
			}
		} catch (Exception e) {
			LogUtil.logException(e, Log.class);
			
			return null;
		}
		
		return null;
		
	}
}
