package com.gym.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import com.gym.dao.UserDAO;
import com.gym.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
public class Register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ BASIC
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        // ✅ PASSWORD CHECK
        if (confirm != null && !password.equals(confirm)) {
            request.setAttribute("msg", "Passwords do not match ❌");
            request.getRequestDispatcher("userregister.jsp").forward(request, response);
            return;
        }

        // ✅ DOB CONVERSION (STRING → DATE)
        Date dob;
        try {
            dob = Date.valueOf(request.getParameter("dob"));
        } catch (Exception e) {
            request.setAttribute("msg", "Invalid Date ❌");
            request.getRequestDispatcher("userregister.jsp").forward(request, response);
            return;
        }

        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // ✅ SAFE NUMBER PARSE
        int weight = 0, height = 0;
        try { weight = Integer.parseInt(request.getParameter("weight")); } catch (Exception ignored) {}
        try { height = Integer.parseInt(request.getParameter("height")); } catch (Exception ignored) {}

        // ✅ FILE UPLOAD
        Part filePart = request.getPart("profile_pic");
        String fileName = "default.png";

        if (filePart != null && filePart.getSize() > 0) {
            String originalName = filePart.getSubmittedFileName();
            fileName = System.currentTimeMillis() + "_" + originalName;

            String uploadPath = getServletContext().getRealPath("/") + "uploads";
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            filePart.write(uploadPath + File.separator + fileName);
        }

        // ✅ SET USER OBJECT
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setDob(dob);                         // ✅ Date
        user.setGender(gender);
        user.setPhone(phone);
        user.setAddress(address);
        user.setWeight(weight);
        user.setHeight(height);
        user.setProfilePic(fileName);
        user.setDoj(Date.valueOf(LocalDate.now())); // ✅ Date

        // ✅ DAO CALL
        UserDAO dao = new UserDAO();
        int r = dao.register(user);

        if (r > 0) {
            request.setAttribute("msg", "User Registered Successfully ✅");
        } else {
            request.setAttribute("msg", "User already exists ❌");
        }

        // ✅ ADMIN FLOW (UNCHANGED)
        HttpSession session = request.getSession(false);
        boolean isAdminFlow = false;

        if (session != null && session.getAttribute("userId") != null) {
            Object obj = session.getAttribute("user");
            if (obj instanceof User) {
                User su = (User) obj;
                if ("admin@gmail.com".equalsIgnoreCase(su.getEmail())) {
                    isAdminFlow = true;
                }
            }
        }

        // ✅ REDIRECT / FORWARD
        if (isAdminFlow) {
            request.getRequestDispatcher("Fetch?page=userdetails.jsp&filter=all")
                   .forward(request, response);
        } else {
            if (r > 0) {
                response.sendRedirect("login.html?registered=1");
            } else {
                request.getRequestDispatcher("userregister.jsp")
                       .forward(request, response);
            }
        }
    }
}