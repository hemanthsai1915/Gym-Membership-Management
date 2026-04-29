package com.gym.servlet;

import org.json.JSONObject;

import com.gym.dao.MembershipDAO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateOrder")
public class CreateOrder extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {

            // ✅ GET USER ID FROM SESSION
            Object userIdObj = req.getSession().getAttribute("userId");
            Integer userId = null;

            if (userIdObj instanceof Integer) {
                userId = (Integer) userIdObj;
            } else if (userIdObj instanceof String) {
                try {
                    userId = Integer.parseInt((String) userIdObj);
                } catch (Exception ignored) {}
            }

            if (userId == null || userId <= 0) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Session expired. Login again.");
                return;
            }

            // ✅ CHECK ACTIVE PLAN BEFORE PAYMENT
            MembershipDAO membershipDAO = new MembershipDAO();

            if (membershipDAO.hasActivePlan(userId)) {
            	res.setContentType("application/json");

            	JSONObject response = new JSONObject();
            	response.put("status", "ACTIVE");
            	response.put("message", "You already have an active membership.");

            	res.getWriter().write(response.toString());
                return;
            }

            // ✅ CONTINUE PAYMENT
            int amount = Integer.parseInt(req.getParameter("amount"));

            RazorpayClient client = new RazorpayClient(
                "rzp_test_ShYQR0oAoEClXB",
                "crmu32isUHthgOSjodbaOMYq"
            );

            JSONObject options = new JSONObject();
            options.put("amount", amount * 100);
            options.put("currency", "INR");

            Order order = client.orders.create(options);

            res.setContentType("application/json");
            res.getWriter().write(order.toString());

        } catch (Exception e) {
            e.printStackTrace();

            try {
                res.setContentType("text/plain");
                res.getWriter().write("ERROR: " + e.getMessage());
            } catch (Exception ignored) {}
        }
    }
}