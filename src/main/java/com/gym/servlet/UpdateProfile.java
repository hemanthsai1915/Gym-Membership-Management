package com.gym.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import com.gym.dao.UserDAO;
import com.gym.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

@MultipartConfig
public class UpdateProfile extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.html");
            return;
        }

        // BASIC INPUTS
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        String dobStr = request.getParameter("dob");
        String weightStr = request.getParameter("weight");
        String heightStr = request.getParameter("height");

        // DAO
        UserDAO dao = new UserDAO();

        // EXISTING USER
        User existing = (User) session.getAttribute("user");
        if (existing == null) {
            existing = dao.getUserByEmail(email);
            if (existing == null) {
                response.sendRedirect("login.html");
                return;
            }
        }

        // ✅ PASSWORD HANDLING
        String finalPassword = existing.getPassword();
        boolean wantsPasswordChange = newPassword != null && !newPassword.isBlank();

        if (wantsPasswordChange) {

            // confirm mismatch
            if (confirm == null || !newPassword.equals(confirm)) {
                response.sendRedirect("userdashboard.jsp?page=profile.jsp&error=2");
                return;
            }

            // wrong current password
            User auth = dao.login(email, currentPassword);
            if (auth == null) {
                response.sendRedirect("userdashboard.jsp?page=profile.jsp&error=1");
                return;
            }

            finalPassword = newPassword;
        }

        // ✅ SAFE PARSING
        int weight = 0, height = 0;
        Date dob = null;

        try {
            if (weightStr != null && !weightStr.isBlank())
                weight = Integer.parseInt(weightStr);
        } catch (Exception ignored) {}

        try {
            if (heightStr != null && !heightStr.isBlank())
                height = Integer.parseInt(heightStr);
        } catch (Exception ignored) {}

        try {
            if (dobStr != null && !dobStr.isBlank())
                dob = Date.valueOf(dobStr);
        } catch (Exception ignored) {}

        // ✅ PROFILE PIC LOGIC
        String profilePic = existing.getProfilePic();

        // 🔥 REMOVE PIC FEATURE
        String removePic = request.getParameter("removePic");
        if ("on".equals(removePic)) {
            profilePic = "default.png";
        }

        // 🔥 NEW UPLOAD (overrides remove if uploaded)
        Part filePart = request.getPart("profilePic");
        if (filePart != null && filePart.getSize() > 0) {

            String originalName = filePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalName;

            String uploadPath = getServletContext().getRealPath("/") + "uploads";
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            filePart.write(uploadPath + File.separator + fileName);

            profilePic = fileName;
        }

        // ✅ SET USER OBJECT
        User u = new User();
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(finalPassword);
        u.setDob(dob);
        u.setPhone(phone);
        u.setAddress(address);
        u.setWeight(weight);
        u.setHeight(height);
        u.setProfilePic(profilePic);

        // ✅ UPDATE DB
        dao.updateUserProfile(u);

        // ✅ UPDATE SESSION
        existing.setUsername(username);
        existing.setPassword(finalPassword);
        existing.setDob(dob);
        existing.setPhone(phone);
        existing.setAddress(address);
        existing.setWeight(weight);
        existing.setHeight(height);
        existing.setProfilePic(profilePic);

        session.setAttribute("user", existing);
        session.setAttribute("msg", "updated");

        // ✅ REDIRECT
        response.sendRedirect("userdashboard.jsp?page=profile.jsp");
    }
}