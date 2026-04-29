<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.gym.model.MembershipView, com.gym.model.Membership" %>

<%
MembershipView membershipView = (MembershipView) request.getAttribute("membershipView");

// fallback safety (never break UI)
if (membershipView == null) {
    membershipView = MembershipView.noPlan();
}

Membership membership = membershipView.getMembership();
%>

<!-- HEADER -->
<div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
    <h4 class="fw-bold mb-0">Your Membership</h4>
    <span class="badge bg-primary-subtle text-primary">Status</span>
</div>

<!-- CARD -->
<div class="card-box p-4 rounded shadow-sm bg-white">

    <!-- STATUS -->
    <div class="text-center mb-4">
        <h6 class="text-muted">Status</h6>

        <h2 class="<%= membershipView.isActive() ? "text-success" : "text-danger" %> fw-bold">
            <%= membershipView.getDisplayStatus() %>
        </h2>

        <% if(membershipView.getDaysRemaining() > 0){ %>
            <span class="badge bg-warning-subtle text-dark mt-2">
                <%= membershipView.getDaysRemaining() %> days remaining
            </span>
        <% } %>
    </div>

    <!-- PROGRESS BAR -->
    <% if(membershipView.hasMembership() && membershipView.getDaysRemaining() > 0){ %>
    <div class="mb-4">
        <h6 class="text-muted">Membership Progress</h6>

        <div class="progress mt-2" style="height: 20px;">
            <div class="progress-bar bg-success fw-bold" 
                 style="width: <%= membershipView.getProgressPercent() %>%;">
                <%= membershipView.getProgressPercent() %>%
            </div>
        </div>
    </div>
    <% } %>

    <!-- DETAILS -->
    <div class="row text-center">

        <div class="col-md-4 mb-3">
            <p class="text-muted mb-1">Plan</p>
            <strong>
                <%= (membership != null && membership.getPlanName() != null) 
                    ? membership.getPlanName() 
                    : "-" %>
            </strong>
        </div>

        <div class="col-md-4 mb-3">
            <p class="text-muted mb-1">Start</p>
            <strong>
                <%= (membership != null && membership.getStartDate() != null) 
                    ? membership.getStartDate().toLocalDate() 
                    : "-" %>
            </strong>
        </div>

        <div class="col-md-4 mb-3">
            <p class="text-muted mb-1">Expiry</p>
            <strong>
                <%= (membership != null && membership.getEndDate() != null) 
                    ? membership.getEndDate().toLocalDate() 
                    : "-" %>
            </strong>
        </div>

    </div>

    <!-- ACTION -->
    <div class="text-center mt-4">

        <% if(membershipView.isActive()){ %>
            <a href="UserDashboard?page=rechargeplan.jsp" class="btn btn-primary px-4">
                Renew Membership
            </a>
        <% } else { %>
            <a href="UserDashboard?page=rechargeplan.jsp" class="btn btn-danger px-4">
                View Recharge Plans
            </a>
        <% } %>

    </div>

</div>