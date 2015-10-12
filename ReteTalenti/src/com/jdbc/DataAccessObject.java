package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DataAccessObject {
	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection != null)
			return connection;
		else {
			// Store the database URL in a string
			ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
			String serverName = rb.getString("dbServer");
//			String portNumber = "1521";
			String sid = rb.getString("dbName");
			String dbUrl = "jdbc:mysql://" + serverName + "/" + sid;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// set the url, username and password for the database
				connection = DriverManager.getConnection(dbUrl, rb.getString("dbUser"),
						rb.getString("dbPassword"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return connection;
		}
	}
}