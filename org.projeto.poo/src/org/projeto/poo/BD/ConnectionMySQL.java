package org.projeto.poo.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionMySQL {
	
	private final static String DB_USER ="root";
	private final static String DB_PASS ="root";
	private final static String DB_ADDRESS ="localhost";
	private final static String DB_SCHEMA ="projetopoo";
	private final static String DB_PORT ="3306";
	
	public Connection getConnection() {
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+DB_ADDRESS+":"+DB_PORT+"/"+DB_SCHEMA, DB_USER, DB_PASS);
			return conn;	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
	
		}
		return null;
	}
	
	public void closeConnection() {
		
	}
}
