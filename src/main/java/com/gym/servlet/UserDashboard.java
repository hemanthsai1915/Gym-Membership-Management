package com.gym.servlet;

import java.io.IOException;

import com.gym.dao.MembershipDAO;
import com.gym.model.MembershipView;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class UserDashboard extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect("login.html");
            return;
        }
        Object userIdObj = session.getAttribute("userId");
        Integer userId = null;
        if (userIdObj instanceof Integer) {
        	userId = (Integer) userIdObj;
        } else if (userIdObj instanceof String) {
        	try {
        		userId = Integer.parseInt((String) userIdObj);
        	} catch (NumberFormatException ignored) {
        		userId = null;
        	}
        }

        MembershipView membershipView = MembershipView.noPlan();
        if (userId != null && userId > 0) {
        	membershipView = new MembershipDAO().getMembershipView(userId);
        }
        request.setAttribute("membershipView", membershipView);

        request.getRequestDispatcher("userdashboard.jsp").forward(request, response);

        
    }
}