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
			Properties prop=new Properties();
			prop.load(new FileInputStream("/home/hemanthsai/eclipse-workspace/Myjars/src/com/DBconnection/jdbc.properties"));
			Class.forName(prop.getProperty("driver"));
			RowSetFactory factory = RowSetProvider.newFactory();
			 jrs = factory.createJdbcRowSet();
			 jrs.setUrl(prop.getProperty("url"));
			 jrs.setUsername(prop.getProperty("username"));
			 jrs.setPassword(prop.getProperty("password"));	
			 return jrs;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return jrs;
		
}
}