package com.gym.servlet;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Logout extends HttpServlet {


	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException {

	        HttpSession session = request.getSession(false);

	        if (session != null) {
	            session.invalidate(); // 🔥 destroy session
	        }

	        response.sendRedirect("login.html");
	    }
	
    
}