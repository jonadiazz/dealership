package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.enums.UserType;
import com.revature.session.Session;
import com.revature.utils.DBConnection;
import com.revature.utils.LogUtil;

import oracle.jdbc.logging.annotations.Log;

public class AccountOracle implements AccountDAO {
	private static Logger log = Logger.getLogger(AccountOracle.class);
	private static DBConnection cu = DBConnection.getDBConnection();

	@Override
	public int addAccount(Account account) {
		int key = 0;
		Connection conn = cu.getConnection();
		log.info("Username: " + account.getUsername());

		try {
			conn.setAutoCommit(false);
			String sql = "insert into accounts (username, password, account_type, account_id) values (?,?,?,account_id.nextval)";
			String[] keys = { "account_id" };
			PreparedStatement stmt = conn.prepareStatement(sql, keys);
			stmt.setString(1, account.getUsername());
			stmt.setString(2, account.getPassword());
			stmt.setString(3, UserType.CUSTOMER.toString());

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				log.info("Account created.");
				key = rs.getInt(1);
				Session.ID = String.valueOf(key);
				account.setId(key);
				conn.commit();
			} else {
				log.trace("Account not created!");
				conn.rollback();
			}

		} catch (SQLException e) {
			LogUtil.rollback(e, conn, AccountOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, AccountOracle.class);
			}
		}

		return key;
	}

	@Override
	public Account getAccount(String username, String password) {
		String sql = "select * from Accounts";

		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if (username.equals(rs.getString("username"))) {
					Account account;

					account = new Account(username, rs.getString("password"));
					account.setId(Integer.valueOf(rs.getString("account_id")));
					account.setAccountType(rs.getString("account_type"));
					Session.ID = rs.getString("account_id");
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
