package com.gym.util;
import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

	
public class MyrowSet{
	public static JdbcRowSet Myrowset() {
		JdbcRowSet jrs = null;
		try {
			// Properties prop=new Properties();
			// prop.load(new FileInputStream("/home/hemanthsai/eclipse-workspace/Myjars/src/com/DBconnection/jdbc.properties"));
			// Class.forName(prop.getProperty("driver"));
			String dbUrl = System.getenv("DB_URL");
String dbUser = System.getenv("DB_USER");
String dbPass = System.getenv("DB_PASSWORD");
			RowSetFactory factory = RowSetProvider.newFactory();
			 jrs = factory.createJdbcRowSet();
			 jrs.setUrl(dbUrl);
			 jrs.setUsername(dbUser);
			 jrs.setPassword(dbPass);	
			 return jrs;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return jrs;
		
}
}
