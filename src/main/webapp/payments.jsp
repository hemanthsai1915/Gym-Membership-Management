<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Vector, com.gym.model.Payments, com.gym.dao.PaymentsDAO, com.gym.model.User" %>

<div class="card-box">
<style>
    .payments-scroll {
        max-height: 340px;
        overflow-y: auto;
        overflow-x: auto;
        border-radius: 10px;
    }
</style>

<div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
    <h5 class="mb-0">Payments</h5>
    <span class="badge bg-secondary-subtle text-secondary">History</span>
</div>

<%
String email = (String) session.getAttribute("email");
User sUser = (User) session.getAttribute("user");
if ((email == null || email.trim().isEmpty()) && sUser != null) {
    email = sUser.getEmail();
}

PaymentsDAO dao = new PaymentsDAO();
Integer userId = null;
Object userIdObj = session.getAttribute("userId");
if (userIdObj instanceof Integer) {
    userId = (Integer) userIdObj;
} else if (userIdObj instanceof String) {
    try { userId = Integer.parseInt((String) userIdObj); } catch (Exception ignored) {}
}
Vector<Payments> payments;
if (userId != null && userId > 0) {
    payments = dao.getuserdataByUserId(userId);
} else {
    payments = dao.getuserdata(email);
}
%>

<% if(payments != null && !payments.isEmpty()){ %>

<div class="table-responsive payments-scroll">
    <table class="table table-hover align-middle mb-0">
        <thead>
            <tr>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
                <th>Payment Id</th>
            </tr>
        </thead>
        <tbody>

        <% for(Payments p : payments){ %>

        <tr>
            <td class="fw-semibold">₹<%= p.getAmount() %></td>

            <td>
                <%
                String payStatus = p.getPayment_status() == null ? "" : p.getPayment_status();
                boolean ok = "PAID".equalsIgnoreCase(payStatus) || "SUCCESS".equalsIgnoreCase(payStatus);
                %>
                <span class="badge <%= ok ? "bg-success-subtle text-success" : "bg-danger-subtle text-danger" %>">
                    <%= p.getPayment_status() %>
                </span>
            </td>

            <td><%= p.getPayment_date() %></td>

            <td><%= p.getPaymentId() != null ? p.getPaymentId() : "-" %></td>
        </tr>

        <% } %>

        </tbody>
    </table>
</div>

<% } else { %>

<p class="text-muted text-center mb-0">No payments found</p>

<% } %>

</div>