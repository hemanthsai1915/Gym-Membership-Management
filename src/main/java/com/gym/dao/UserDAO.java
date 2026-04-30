package com.gym.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

import com.gym.model.AdminUserRow;
import com.gym.model.ProjectDesign;
import com.gym.model.User;
import com.gym.util.Myjdbc;

public class UserDAO implements ProjectDesign {

    @Override
    public User login(String email, String password) {
        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "select * from gym_users where email=? and password=?"
            );

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setPhone(rs.getString("phone"));
                u.setDoj(rs.getDate("doj"));
                u.setDob(rs.getDate("dob"));
                u.setHeight(rs.getInt("height"));
                u.setWeight(rs.getInt("weight"));
                u.setProfilePic(rs.getString("profile_pic"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int register(User u) {
        int r = 0;
        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO gym_users(username,email,password,phone,gender,dob,weight,height,profile_pic,doj,address) VALUES(?,?,?,?,?,?,?,?,?,?,?)"
            );

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getGender());

            if (u.getDob() != null)
                ps.setDate(6, u.getDob());

            ps.setInt(7, u.getWeight());
            ps.setInt(8, u.getHeight());
            ps.setString(9, u.getProfilePic());
            ps.setDate(10, java.sql.Date.valueOf(LocalDate.now()));
            ps.setString(11, u.getAddress());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Vector<AdminUserRow> fetch(String filter) {

        Vector<AdminUserRow> rows = new Vector<>();

        if (filter == null || filter.isBlank()) {
            filter = "all";
        }

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "SELECT u.id AS user_id, u.username, u.email, u.phone, " +
                "m.plan_name, m.start_date, m.end_date " +
                "FROM gym_users u " +
                "LEFT JOIN (" +
                "  SELECT mm.user_id, mm.plan_name, mm.start_date, mm.end_date " +
                "  FROM membership mm " +
                "  INNER JOIN (" +
                "    SELECT user_id, MAX(end_date) AS max_end FROM membership GROUP BY user_id" +
                "  ) latest ON latest.user_id = mm.user_id AND latest.max_end = mm.end_date" +
                ") m ON m.user_id = u.id " +
                "WHERE u.email <> ? ORDER BY u.id DESC"
            );

            ps.setString(1, "admin@gmail.com");

            ResultSet jrs = ps.executeQuery();

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

                if (endRaw == null || startRaw == null) {
                    r.setMembershipStatus("NO PLAN");
                    r.setDaysLeft(0);
                } else {
                    LocalDate today = LocalDate.now();
                    LocalDate start = LocalDate.parse(startRaw.split(" ")[0]);
                    LocalDate end = LocalDate.parse(endRaw.split(" ")[0]);

                    long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, end);
                    if (daysLeft < 0) daysLeft = 0;

                    r.setDaysLeft((int) daysLeft);

                    if (today.isBefore(start)) {
                        r.setMembershipStatus("UPCOMING");
                    } else if (today.isAfter(end)) {
                        r.setMembershipStatus("EXPIRED");
                    } else {
                        r.setMembershipStatus("ACTIVE");
                    }
                }

                rows.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public User getUserByEmail(String email) {

        User user = null;

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM gym_users WHERE email=?"
            );

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setDob(rs.getDate("dob"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setWeight(rs.getInt("weight"));
                user.setHeight(rs.getInt("height"));
                user.setDoj(rs.getDate("doj"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public int userCount() {
        int count = 0;

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "select count(email) from gym_users where email!=?"
            );

            ps.setString(1, "admin@gmail.com");

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
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

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM gym_users WHERE email=?"
            );

            ps.setString(1, email);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
