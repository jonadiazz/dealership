package com.revature.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.revature.login.Validate;

import services.DBConnection;
import services.LogUtil;

import com.revature.driver.Driver;
import com.revature.login.Login;
import org.apache.log4j.Logger;

public class Login {
	
	private String username;
	private String password;
	
	private static Logger log = Logger.getLogger(Login.class);
	private static DBConnection connection = DBConnection.getDBConnection();
	
	final static Logger logger = Logger.getLogger(Login.class);


	public void retrieveUserInputUsername() {
		username = Validate.getInstance().validateUsername();
		// TODO Auto-generated method stub
	}


	public void retrieveUserInputPassword() {
		password = Validate.getInstance().validatePassword();
		// TODO Auto-generated method stub
	}

	public boolean signIn() {
		String sql = "select * from Cars";
		
		try (Connection conn = connection.getConnection()) {
			log.trace(sql);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				log.trace(rs.getString(1) + " " + rs.getString(2));
			}
			
		} catch (Exception e) {
			LogUtil.logException(e, Login.class);
		}
		// TODO: Connect to database using username and password
		// if failed to sign in, return to userMenu
		logger.warn("Sign in failed. Register an account!");
		return true;
		// TODO Auto-generated method stub
		
	}
	

}
