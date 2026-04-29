package com.gym.util;

import java.util.Properties;
import java.sql.*;
import java.io.FileInputStream;


public class Myjdbc{
	public static Connection myconn() {
		Connection con=null;
		try {
			
			//load properties file
			Properties prop=new Properties();
			prop.load(new FileInputStream("/home/hemanthsai/eclipse-workspace/Myjars/src/com/DBconnection/jdbc.properties"));
			Class.forName(prop.getProperty("driver"));
			con = DriverManager.getConnection(
		                prop.getProperty("url"),
		                prop.getProperty("username"),
		                prop.getProperty("password")
		            );
			
			 }
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	

	}
}