package com.gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gym.model.EventData;
import com.gym.model.EventDesign;
import com.gym.util.Myjdbc;

public class EventDAO implements EventDesign {

    @Override
    public void insertEvent(EventData event) {
        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO events(title, description, event_date, is_new) VALUES (?, ?, ?, true)"
            );

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getDate());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEvent(int id) {
        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM events WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean countNotification() {

        boolean hasNew = false;

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM events WHERE is_new = true"
            );

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    hasNew = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNew;
    }

    @Override
    public List<EventData> viewEvent(String filter) {

        List<EventData> list = new ArrayList<>();

        try {
            Connection con = Myjdbc.myconn();

            String query = "SELECT * FROM events";

            if ("upcoming".equals(filter)) {
                query += " WHERE event_date >= CURDATE()";
            } else if ("today".equals(filter)) {
                query += " WHERE event_date = CURDATE()";
            } else if ("past".equals(filter)) {
                query += " WHERE event_date < CURDATE()";
            }

            query += " ORDER BY event_date ASC";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                EventData e = new EventData();

                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setDate(rs.getString("event_date"));

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void resetNotification() {

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE events SET is_new = false"
            );

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int Eventcount() {

        int count = 0;

        try {
            Connection con = Myjdbc.myconn();

            PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM events WHERE event_date >= CURRENT_DATE"
            );

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
