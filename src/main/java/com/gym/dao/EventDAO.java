package com.gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JdbcRowSet;

import com.gym.model.EventData;
import com.gym.model.EventDesign;
import com.gym.util.Myjdbc;
import com.gym.util.MyrowSet;


public class EventDAO implements EventDesign {

	@Override
	public void insertEvent(EventData event) {
		Connection connection=Myjdbc.myconn();
		try {
		PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO events(title, description, event_date, is_new) VALUES (?, ?, ?, true)");
		preparedStatement.setString(1,event.getTitle() );
		preparedStatement.setString(2, event.getDescription());
		preparedStatement.setString(3, event.getDate());
		preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEvent(int id) {
		Connection connection=Myjdbc.myconn();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM events WHERE id=?");
			preparedStatement.setInt(1,id);
			preparedStatement.executeUpdate();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean countNotification() {
		boolean hasNew=false;
		JdbcRowSet jRowSet=MyrowSet.Myrowset();
		try {
			jRowSet.setCommand("SELECT COUNT(*) FROM events WHERE is_new = true");
			jRowSet.execute();
			
			if(jRowSet.next())
			{
				if(jRowSet.getInt(1)>0)
				 {
					 hasNew=true;
				 }
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return hasNew;
	}
@Override
	public List<EventData> viewEvent(String filter) {

	    List<EventData> aList = new ArrayList<>();
	    JdbcRowSet jSet = MyrowSet.Myrowset();

	    try {

	        String query = "SELECT * FROM events";

	        if ("upcoming".equals(filter)) {
	            query += " WHERE event_date >= CURDATE()";
	        } 
	        else if ("today".equals(filter)) {
	            query += " WHERE event_date = CURDATE()";
	        } 
	        else if ("past".equals(filter)) {
	            query += " WHERE event_date < CURDATE()";
	        }

	        // 🔥 Smart ordering
	        query += " ORDER BY event_date ASC";

	        jSet.setCommand(query);
	        jSet.execute();

	        while (jSet.next()) {

	            EventData eData = new EventData();
	            eData.setId(jSet.getInt("id"));
	            eData.setTitle(jSet.getString("title"));
	            eData.setDescription(jSet.getString("description"));
	            eData.setDate(jSet.getString("event_date"));

	            aList.add(eData);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return aList;
	}

	@Override
	public void resetNotification() {

	    try {
	        Connection con = Myjdbc.myconn();

	        PreparedStatement ps = con.prepareStatement("UPDATE events SET is_new = false");

	        ps.executeUpdate();

	        ps.close();
	        con.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	@Override
	public int Eventcount() {
		 JdbcRowSet jRowSet=MyrowSet.Myrowset();
		 int count=0;
		try {
			jRowSet.setCommand("SELECT COUNT(*) FROM events where event_date>=CURRENT_DATE");
			jRowSet.execute();
			
			if(jRowSet.next())
			{
				count=jRowSet.getInt(1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}

}