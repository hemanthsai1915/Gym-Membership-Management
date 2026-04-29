package com.gym.util;
import java.sql.*;
import javax.sql.rowset.JdbcRowSet;
public class Execute {

	public static void main(String[] args) {
		Connection b;
		JdbcRowSet c;
		try {
			b = Myjdbc.myconn();
			System.out.println(b);
			c=MyrowSet.Myrowset();
			System.out.println(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
