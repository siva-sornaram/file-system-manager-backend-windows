package com.siva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.jdbc.Driver"); -- This is Depreciated
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/logindemo", "vicky", "77vicky77");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		getConnection();
		System.out.println("Connected");
	}
}