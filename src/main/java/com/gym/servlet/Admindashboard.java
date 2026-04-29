package com.gym.servlet;

import java.io.IOException;

import com.gym.dao.EventDAO;
import com.gym.dao.MembershipDAO;
import com.gym.dao.PaymentsDAO;
import com.gym.dao.UserDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Admindashboard extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 DO NOT create new session
        HttpSession hs = request.getSession(false);

        // 🔥 Check session properly
        if (hs == null || hs.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }
        EventDAO ed=new EventDAO();
        int eventcount=ed.Eventcount();
        MembershipDAO mb = new MembershipDAO();
        int memcount = mb.MemberCount();
        int expiredcount = mb.expiredcount();
        UserDAO ud = new UserDAO();
        int totalCount = ud.userCount();
        PaymentsDAO dao = new PaymentsDAO();

        double totalRevenue = dao.getTotalRevenue();
        double todayRevenue = dao.getTodayRevenue();
        int totalTransactions = dao.getTotalTransactions();
        
        hs.setAttribute("totalUsers", totalCount);
        hs.setAttribute("activeMembers", memcount);
        hs.setAttribute("eventsCount", eventcount);
        hs.setAttribute("expiredMembers", expiredcount);
        hs.setAttribute("totalRevenue", totalRevenue);
        hs.setAttribute("todayRevenue", todayRevenue);
        hs.setAttribute("totalTransactions", totalTransactions);
        String page = request.getParameter("page");
        if (page != null && !page.trim().isEmpty()) {
            request.setAttribute("page", page);
            RequestDispatcher rd = request.getRequestDispatcher("admindashboard.jsp?page=" + page);
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("admindashboard.jsp");
        rd.forward(request, response);

    }
}