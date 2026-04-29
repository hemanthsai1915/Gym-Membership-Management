package com.gym.servlet;

import java.io.IOException;
import java.sql.Date;

import com.gym.dao.UserDAO;
import com.gym.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Update extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);

        request.setAttribute("user", user);
        request.getRequestDispatcher("admindashboard.jsp?page=updateuser.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        // DATE SAFE
        Date dob = null, doj = null;
        try {
            String d = request.getParameter("dob");
            if (d != null && !d.isBlank()) dob = Date.valueOf(d);
        } catch (Exception ignored) {}

        try {
            String j = request.getParameter("doj");
            if (j != null && !j.isBlank()) doj = Date.valueOf(j);
        } catch (Exception ignored) {}

        // NUMBER SAFE
        int weight = 0, height = 0;
        try {
            String w = request.getParameter("weight");
            if (w != null && !w.isBlank()) weight = Integer.parseInt(w);
        } catch (Exception ignored) {}

        try {
            String h = request.getParameter("height");
            if (h != null && !h.isBlank()) height = Integer.parseInt(h);
        } catch (Exception ignored) {}

        // PASSWORD SAFE
        String password = request.getParameter("password");

        User user = new User();
        user.setEmail(email);
        user.setUsername(request.getParameter("username"));

        if (password != null && !password.isBlank()) {
            user.setPassword(password);
        }

        user.setDob(dob);
        user.setGender(request.getParameter("gender"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
        user.setWeight(weight);
        user.setHeight(height);
        user.setDoj(doj);

        UserDAO dao = new UserDAO();
        dao.updateUserByEmail(user);

        response.sendRedirect("Fetch");
    }
}