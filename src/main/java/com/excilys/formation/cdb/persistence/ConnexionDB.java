package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum ConnexionDB {
	
	INSTANCE;
	
	private Connection conn;
	
	private ConnexionDB () {

	}
	
	public Connection getConnection() {
		Properties prop = new Properties();
		String fileConf = "config/db/config.properties";
				
		try(FileInputStream fis = new FileInputStream(fileConf);){
			prop.load(fis);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String urlDB = prop.getProperty("mysql.url");
			String user = prop.getProperty("mysql.user");
			String passwd = prop.getProperty("mysql.passwd");
			conn = DriverManager.getConnection(urlDB, user, passwd);
			
			/*
			 * Log into file : Connection succeded in data base
			 */
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return conn;
	}
	
	public static void main (String[] args) throws SQLException {
		Connection conn = ConnexionDB.INSTANCE.getConnection();
		conn.close();
	}
	
}