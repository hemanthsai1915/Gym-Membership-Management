package com.gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;

import com.gym.model.AdminUserRow;
import com.gym.model.ProjectDesign;
import com.gym.model.User;
import com.gym.util.Myjdbc;
import com.gym.util.MyrowSet;


public class UserDAO implements ProjectDesign{

	@Override
	public User login(String email,String password) {
		JdbcRowSet jrs=MyrowSet.Myrowset();
		try {
			jrs.setCommand("select * from gym_users where email=? and password=?");
			jrs.setString(1, email);
			jrs.setString(2,password);
			jrs.execute();
			if(jrs.next())
			{
				User u=new User();
				u.setId(jrs.getInt("id"));
				u.setUsername(jrs.getString("username"));
				u.setEmail(jrs.getString("email"));
				u.setPassword(jrs.getString("password"));
				u.setPhone(jrs.getString("phone"));
				u.setDoj(jrs.getDate("doj"));
				u.setDob(jrs.getDate("dob"));
				u.setHeight(jrs.getInt("height"));
				u.setWeight(jrs.getInt("weight"));
				u.setProfilePic(jrs.getString("profile_pic")); 
				return u;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
@Override
	public int register(User u){
	    int r = 0;
	    try{
	        Connection con = Myjdbc.myconn();

	        PreparedStatement ps = con.prepareStatement(
	        		"INSERT INTO gym_users(username,email,password,phone,gender,dob,weight,height,profile_pic,doj,address) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

	        		ps.setString(1, u.getUsername());
	        		ps.setString(2, u.getEmail());
	        		ps.setString(3, u.getPassword());
	        		ps.setString(4, u.getPhone());     // ✅ correct
	        		ps.setString(5, u.getGender()); 
	        		if(u.getDob() != null)
	        		    ps.setDate(6, u.getDob());// ✅ correct
	        		ps.setInt(7, u.getWeight());
	        		ps.setInt(8, u.getHeight());
	        		ps.setString(9, u.getProfilePic());
	        		ps.setDate(10, java.sql.Date.valueOf(LocalDate.now()));
	        		ps.setString(11,u.getAddress());

	        r = ps.executeUpdate();

	    }catch(Exception e){ e.printStackTrace(); }

	    return r;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Vector<AdminUserRow> fetch(String filter) {

	    JdbcRowSet jrs = MyrowSet.Myrowset();
	    Vector<AdminUserRow> rows = new Vector<>();

	    if (filter == null || filter.isBlank()) {
	        filter = "all";
	    }

	    try {

	        jrs.setCommand(
	            "SELECT " +
	            "  u.id AS user_id, u.username, u.email, u.phone, " +
	            "  m.plan_name, m.start_date, m.end_date " +
	            "FROM gym_users u " +
	            "LEFT JOIN (" +
	            "  SELECT mm.user_id, mm.plan_name, mm.start_date, mm.end_date " +
	            "  FROM membership mm " +
	            "  INNER JOIN (" +
	            "    SELECT user_id, MAX(end_date) AS max_end " +
	            "    FROM membership GROUP BY user_id" +
	            "  ) latest ON latest.user_id = mm.user_id AND latest.max_end = mm.end_date" +
	            ") m ON m.user_id = u.id " +
	            "WHERE u.email <> ? " +
	            "ORDER BY u.id DESC"
	        );

	        jrs.setString(1, "admin@gmail.com");
	        jrs.execute();

	        while (jrs.next()) {

	            AdminUserRow r = new AdminUserRow();

	            r.setUserId(jrs.getInt("user_id"));
	            r.setUsername(jrs.getString("username"));
	            r.setEmail(jrs.getString("email"));
	            r.setPhone(jrs.getString("phone"));
	            r.setPlanName(jrs.getString("plan_name"));
	            r.setStartDate(jrs.getString("start_date"));
	            r.setEndDate(jrs.getString("end_date"));

	            String startRaw = r.getStartDate();
	            String endRaw = r.getEndDate();

	            if (endRaw == null || endRaw.isBlank() ||
	                startRaw == null || startRaw.isBlank()) {

	                r.setMembershipStatus("NO PLAN");
	                r.setDaysLeft(0);

	            } else {

	                java.time.LocalDate today = java.time.LocalDate.now();
	                java.time.LocalDate start = java.time.LocalDate.parse(startRaw.split(" ")[0]);
	                java.time.LocalDate end = java.time.LocalDate.parse(endRaw.split(" ")[0]);

	                long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, end);
	                if (daysLeft<0) {
	                	daysLeft=0;
	                }
	                r.setDaysLeft((int) daysLeft);
	                

	                if (today.isBefore(start)) {
	                    r.setMembershipStatus("UPCOMING");
	                } else if (today.isAfter(end)) {
	                    r.setMembershipStatus("EXPIRED");
	                } else {
	                    r.setMembershipStatus("ACTIVE");
	                }
	            }

	            // ✅ FILTER LOGIC
	            if ("active".equalsIgnoreCase(filter) &&
	                !"ACTIVE".equalsIgnoreCase(r.getMembershipStatus())) continue;

	            if ("expired".equalsIgnoreCase(filter) &&
	                !"EXPIRED".equalsIgnoreCase(r.getMembershipStatus())) continue;

	            if ("noplan".equalsIgnoreCase(filter) &&
	                !"NO PLAN".equalsIgnoreCase(r.getMembershipStatus())) continue;

	            rows.add(r);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // ✅ FALLBACK (NO MEMBERSHIP)
	    if (rows.isEmpty()) {
	        try {
	            jrs = MyrowSet.Myrowset();

	            jrs.setCommand(
	                "SELECT id AS user_id, username, email, phone " +
	                "FROM gym_users WHERE email <> ? ORDER BY id DESC"
	            );

	            jrs.setString(1, "admin@gmail.com");
	            jrs.execute();

	            while (jrs.next()) {

	                AdminUserRow r = new AdminUserRow();

	                r.setUserId(jrs.getInt("user_id"));
	                r.setUsername(jrs.getString("username"));
	                r.setEmail(jrs.getString("email"));
	                r.setPhone(jrs.getString("phone"));

	                r.setPlanName(null);
	                r.setStartDate(null);
	                r.setEndDate(null);
	                r.setMembershipStatus("NO PLAN");
	                r.setDaysLeft(0);

	                // apply filter again
	                if ("noplan".equalsIgnoreCase(filter) || "all".equalsIgnoreCase(filter)) {
	                    rows.add(r);
	                }
	            }

	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }

	    return rows;
	}
	@Override
	public User getUserByEmail(String email) {

	    User user = null;
	    JdbcRowSet jrs = MyrowSet.Myrowset();

	    try {
	        jrs.setCommand("SELECT * FROM gym_users WHERE email=?");
	        jrs.setString(1, email);
	        jrs.execute();

	        if (jrs.next()) {
	            user = new User();
	            user.setEmail(jrs.getString("email"));
	            user.setUsername(jrs.getString("username"));
	            user.setPassword(jrs.getString("password"));
	            user.setDob(jrs.getDate("dob"));
	            user.setGender(jrs.getString("gender"));
	            user.setPhone(jrs.getString("phone"));
	            user.setAddress(jrs.getString("address"));
	            user.setWeight(jrs.getInt("weight"));
	            user.setHeight(jrs.getInt("height"));
	            user.setDoj(jrs.getDate("doj"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	

	@Override
	public int userCount() {
		 int count = 0;
		JdbcRowSet jrs=MyrowSet.Myrowset();
		try {
			
			jrs.setCommand("select count(email) from gym_users where email!=? ");
			jrs.setString(1, "admin@gmail.com");
			jrs.execute();
			if(jrs.next())
			{
				 count = jrs.getInt(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void updateUserByEmail(User user) {

	    try {
	        Connection con = Myjdbc.myconn();

	        PreparedStatement ps = con.prepareStatement(
	            "UPDATE gym_users SET username=?, password=?, dob=?, gender=?, phone=?, address=?, weight=?, height=?, doj=?, profile_pic=? WHERE email=?"
	        );

	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getPassword());
	        ps.setDate(3, user.getDob());
	        ps.setString(4, user.getGender());
	        ps.setString(5, user.getPhone());
	        ps.setString(6, user.getAddress());
	        ps.setInt(7, user.getWeight());
	        ps.setInt(8, user.getHeight());
	        ps.setDate(9, user.getDoj());
	        ps.setString(10, user.getProfilePic());
	        ps.setString(11, user.getEmail());
	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@Override
	public void updateUserProfile(User user) {

	    try {
	        Connection con = Myjdbc.myconn();

	        PreparedStatement ps = con.prepareStatement(
	            "UPDATE gym_users SET username=?, password=?, dob=?, phone=?, address=?, weight=?, height=?, profile_pic=? WHERE email=?"
	        );

	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getPassword());
	        ps.setDate(3, user.getDob());
	        ps.setString(4, user.getPhone());
	        ps.setString(5, user.getAddress());
	        ps.setInt(6, user.getWeight());
	        ps.setInt(7, user.getHeight());
	        ps.setString(8, user.getProfilePic());
	        ps.setString(9, user.getEmail());

	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@Override
	public void delete(String email) {
		 
		Connection connection=Myjdbc.myconn();
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM gym_users WHERE email=?");
			preparedStatement.setString(1, email);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
