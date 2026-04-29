package com.gym.servlet;

import java.io.IOException;
import java.time.LocalDate;

import org.json.JSONObject;

import com.gym.dao.MembershipDAO;
import com.gym.dao.PaymentsDAO;
import com.gym.model.Membership;
import com.gym.model.Payments;
import com.razorpay.Utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VerifyPayment")
public class VerifyPayment extends HttpServlet {

    private static final String KEY_SECRET =
            System.getenv().getOrDefault("RAZORPAY_KEY_SECRET", "crmu32isUHthgOSjodbaOMYq");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");

        // 🔐 SESSION CHECK
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

        try {
            // 🔽 PARAMS
            String paymentId = coalesce(req.getParameter("payment_id"),
                                       req.getParameter("razorpay_payment_id"));

            String orderId = coalesce(req.getParameter("order_id"),
                                     req.getParameter("razorpay_order_id"));

            String signature = coalesce(req.getParameter("signature"),
                                       req.getParameter("razorpay_signature"));

            String plan = req.getParameter("plan");
            String status = req.getParameter("status");

            int amount = parseIntSafe(req.getParameter("amount"));
            int days = parseIntSafe(req.getParameter("days"));

            if (status == null) status = "FAILED";

            // 🔥 STEP 1: HANDLE FAILED FIRST
            if ("FAILED".equals(status)) {

                Payments payment = new Payments();
                payment.setUserId(userId);
                payment.setAmount(amount);
                payment.setPaymentId(
                        paymentId != null ? paymentId : "FAILED_" + System.currentTimeMillis());
                payment.setOrderId(
                        orderId != null ? orderId : "FAILED");
                payment.setStatus("FAILED");

                new PaymentsDAO().savePayment(payment);

                res.getWriter().write("FAILED SAVED");
                return;
            }

            // 🔥 STEP 2: VERIFY SIGNATURE FOR SUCCESS
            boolean validSignature = false;

            try {
                if (paymentId != null && orderId != null && signature != null) {

                    JSONObject options = new JSONObject();
                    options.put("razorpay_order_id", orderId);
                    options.put("razorpay_payment_id", paymentId);
                    options.put("razorpay_signature", signature);

                    validSignature = Utils.verifyPaymentSignature(options, KEY_SECRET);
                }
            } catch (Exception ignored) {}

            if (!validSignature) {
                res.getWriter().write("Invalid payment signature");
                return;
            }

            // 🔥 STEP 3: SAVE SUCCESS PAYMENT
            Payments payment = new Payments();
            payment.setUserId(userId);
            payment.setAmount(amount);
            payment.setPaymentId(paymentId);
            payment.setOrderId(orderId);
            payment.setStatus("SUCCESS");

            new PaymentsDAO().savePayment(payment);

            // 🔥 STEP 4: CREATE MEMBERSHIP
            MembershipDAO membershipDAO = new MembershipDAO();

            if (!membershipDAO.hasActivePlan(userId)) {

            LocalDate start = LocalDate.now();
            LocalDate end = start.plusDays(days);

            Membership membership = new Membership();
            membership.setUserId(userId);
            membership.setPlanName(plan);
            membership.setStartDate(java.sql.Date.valueOf(start));
            membership.setEndDate(java.sql.Date.valueOf(end));
            membership.setStatus("ACTIVE");

            membershipDAO.createMembership(membership);

            res.getWriter().write("Payment successful. Membership activated.");
            }
            else {
                res.getWriter().write("Payment successful, but membership already active.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("Something went wrong.");
        }
    }

    // 🧠 HELPERS
    private static String coalesce(String a, String b) {
        return (a != null && !a.trim().isEmpty()) ? a : b;
    }

    private static int parseIntSafe(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }
}