package com.gym.servlet;

import java.io.IOException;
import java.util.List;

import com.gym.dao.EventDAO;
import com.gym.dao.MembershipDAO;
import com.gym.dao.UserDAO;
import com.gym.model.EventData;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewEventsAdmin extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String filter = request.getParameter("filter");

    	if (filter == null) {
    	    filter = "all";
    	}
    	EventDAO ed=new EventDAO();
        List<EventData> events = ed.viewEvent(filter);
        HttpSession hs = request.getSession(false);
        if (hs != null && hs.getAttribute("userId") != null) {
            UserDAO ud = new UserDAO();
            MembershipDAO md = new MembershipDAO();
            hs.setAttribute("totalUsers", ud.userCount());
            hs.setAttribute("activeMembers", md.MemberCount());
            hs.setAttribute("eventsCount", ed.Eventcount());
        }
       
        request.setAttribute("events", events);
        RequestDispatcher rd = request.getRequestDispatcher("admindashboard.jsp?page=adminevents.jsp");
        rd.forward(request, response);
    }
}