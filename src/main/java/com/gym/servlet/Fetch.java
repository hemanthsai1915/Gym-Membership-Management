package com.gym.servlet;

import java.io.IOException;
import java.util.Vector;

import com.gym.dao.EventDAO;
import com.gym.dao.MembershipDAO;
import com.gym.dao.UserDAO;
import com.gym.model.AdminUserRow;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Fetch extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession hs = request.getSession(false);

        if (hs == null || hs.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // ✅ GET FILTER
        String filter = request.getParameter("filter");

        if (filter == null || filter.isBlank()) {
            filter = "all";
        }

        UserDAO dao = new UserDAO();

        Vector<AdminUserRow> users = dao.fetch(filter); // ✅ PASS FILTER

        MembershipDAO membershipDAO = new MembershipDAO();
        EventDAO eventDAO = new EventDAO();

        hs.setAttribute("totalUsers", dao.userCount());
        hs.setAttribute("activeMembers", membershipDAO.MemberCount());

        request.setAttribute("eventsCount", eventDAO.viewEvent("all").size());
        request.setAttribute("users", users);

        // ✅ IMPORTANT FOR EXPORT BUTTON
        request.setAttribute("selectedFilter", filter);

        RequestDispatcher rd =
                request.getRequestDispatcher("admindashboard.jsp?page=userdetails.jsp");

        rd.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession hs = request.getSession(false);

        if (hs == null || hs.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // ✅ GET FILTER
        String filter = request.getParameter("filter");

        if (filter == null || filter.isBlank()) {
            filter = "all";
        }

        UserDAO dao = new UserDAO();

        Vector<AdminUserRow> users = dao.fetch(filter); // ✅ PASS FILTER

        MembershipDAO membershipDAO = new MembershipDAO();
        EventDAO eventDAO = new EventDAO();

        hs.setAttribute("totalUsers", dao.userCount());
        hs.setAttribute("activeMembers", membershipDAO.MemberCount());

        request.setAttribute("eventsCount", eventDAO.viewEvent("all").size());
        request.setAttribute("users", users);

        // ✅ IMPORTANT FOR EXPORT BUTTON
        request.setAttribute("selectedFilter", filter);

        RequestDispatcher rd =
                request.getRequestDispatcher("admindashboard.jsp?page=userdetails.jsp");

        rd.forward(request, response);
    }
}