package com.gym.servlet;

import java.io.IOException;

import com.gym.dao.UserDAO;
import com.gym.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Login extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		UserDAO ud=new UserDAO();
		User user=ud.login(email,password); 

		int totalCount=0;
		totalCount=ud.userCount();

		HttpSession hs=request.getSession();
		

		if(user!=null)
		{
			 hs.setAttribute("email",email);
			 hs.setAttribute("user", user);
			 hs.setAttribute("userId", user.getId());
			if(email.equals("admin@gmail.com"))
			{

			    response.sendRedirect("Admindashboard"); // ✅ MUST
			    return;
			}
			else {
		        response.sendRedirect("UserDashboard");
		    }
		}
		else {
			response.sendRedirect("login.html?error=1");
		}
	}
	
	
	

}
