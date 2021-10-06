package com.siva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logindao {

	public static boolean validate(String uname , String pass) {
		
		String sql = "SELECT * FROM usernames WHERE username = ? and password = ?";
		int i = 1;
		
		
		
		try {
			
			Connection con = DbConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(i++, uname);
			stmt.setString(i++, pass);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	public static User getUser(String uname, String pass) {
		String selectSql = "SELECT roleid FROM usernames WHERE username = ? and password = ?";
		int i = 1;

		User user;

		try {
			
			Connection con = DbConnection.getConnection();
			PreparedStatement stmt = con.prepareStatement(selectSql);
			stmt.setString(i++, uname);
			stmt.setString(i++, pass);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				user = new User(uname, true, rs.getInt(1));
				return user;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void main(String[] args) {
		String uname = "siva", pass = "MTIzNDU2";
		System.out.println(validate(uname,pass));
	}

}