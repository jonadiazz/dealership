package com.revature.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

	private static DBConnection cu = null;
	private static Properties prop;

	private DBConnection() {
		prop = new Properties();

		try {
//				TODO: use class loader to reduce reliance on the file system
			InputStream dbProps = DBConnection.class.getClassLoader().getResourceAsStream("database.properties");
			prop.load(dbProps);

		} catch (Exception e) {
//				TODO: log exceptions
			LogUtil.logException(e, DBConnection.class);
		}
	}

	public static synchronized DBConnection getDBConnection() {

		if (cu == null) {
			cu = new DBConnection();
		}

		return cu;
	}

	public Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName(prop.getProperty("DRIVER"));
			conn = DriverManager.getConnection(prop.getProperty("ENDPOINT"), prop.getProperty("USER"),
					prop.getProperty("PASSWORD"));

		} catch (Exception e) {
			LogUtil.logException(e, DBConnection.class);
		}

		return conn;
	}

}