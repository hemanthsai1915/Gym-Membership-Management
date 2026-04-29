package com.gym.servlet;

import java.io.IOException;

import com.gym.dao.EventDAO;
import com.gym.model.EventData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddEvent extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        
        if (title == null || description == null || date == null ||
                title.isEmpty() || description.isEmpty() || date.isEmpty()) {
        	response.sendRedirect("ViewEventsAdmin?page=adminevents.jsp");
            return;
            }
        EventData event=new EventData();
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(date);
        
        EventDAO ed=new EventDAO();
        ed.insertEvent(event);
        response.sendRedirect("ViewEventsAdmin?page=adminevents.jsp");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("Admindashboard?page=addevent.jsp");
    }
}