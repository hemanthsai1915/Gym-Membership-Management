package com.gym.servlet;

import java.io.IOException;
import java.util.List;

import com.gym.dao.EventDAO;
import com.gym.model.EventData;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewEventsUser extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EventDAO dao = new EventDAO();

        dao.resetNotification();

        List<EventData> events = dao.viewEvent("upcoming");

        request.setAttribute("events", events);

        RequestDispatcher rd = request.getRequestDispatcher("userdashboard.jsp?page=userevents.jsp");
        rd.forward(request, response);
    }
}