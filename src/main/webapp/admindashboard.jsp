<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

String currentPage = request.getParameter("page");
if (currentPage == null || currentPage.trim().isEmpty()) {
    Object pageAttr = request.getAttribute("page");
    if (pageAttr != null) currentPage = pageAttr.toString();
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

<style>
body { margin: 0; background: #0b1220; }

.sidebar {
    height: 100vh;
    background: linear-gradient(180deg, #0f172a, #111827 45%, #0b1220);
    color: #e5e7eb;
    padding: 18px 14px;
    border-right: 1px solid rgba(148, 163, 184, 0.18);
}

.sidebar h4 {
    font-weight: 800;
    margin-bottom: 18px;
    color: #ffffff;
}

.sidebar a {
    color: rgba(226, 232, 240, 0.82);
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px 12px;
    border-radius: 12px;
    margin: 6px 0;
}

.sidebar a:hover {
    background: rgba(59, 130, 246, 0.18);
    color: #ffffff !important;
}

.sidebar a.active-link {
    background: linear-gradient(120deg, rgba(37, 99, 235, .30), rgba(16, 185, 129, .24));
    color: #ffffff !important;
    font-weight: 600;
}

.content {
    padding: 22px 24px;
    background: linear-gradient(145deg, #0b1220, #0f172a 55%, #111827);
    min-height: 100vh;
    color: #e2e8f0;
}

.subtitle {
    color: rgba(203, 213, 225, 0.85);
    font-size: 14px;
    margin-bottom: 12px;
}

.stat-card {
    border-radius: 16px;
    border: 1px solid rgba(148, 163, 184, 0.22);
    background: linear-gradient(160deg, rgba(30, 41, 59, 0.74), rgba(15, 23, 42, 0.64));
    padding: 15px;
    text-align: center;
}

.panel {
    border: 1px solid rgba(148, 163, 184, 0.22);
    background: linear-gradient(160deg, rgba(30, 41, 59, 0.72), rgba(15, 23, 42, 0.64));
    border-radius: 16px;
    padding: 14px;
}

/* Dark table */
.panel .table {
    --bs-table-bg: transparent;
    --bs-table-color: #e2e8f0;
    --bs-table-hover-bg: rgba(59, 130, 246, 0.12);
    --bs-table-hover-color: #ffffff;
    --bs-table-striped-bg: rgba(30, 41, 59, 0.6);
}

.panel .table thead {
    background: rgba(59, 130, 246, 0.15);
}

.panel .table th {
    color: #93c5fd;
}

.panel .table td {
    color: #e2e8f0;
}
</style>
</head>

<body>

<div class="container-fluid">
<div class="row">

<!-- Sidebar -->
<div class="col-md-3 col-lg-2 sidebar">

<h4 class="text-center mb-4">Admin Panel</h4>

<a class="<%= "userregister.jsp".equals(currentPage) ? "active-link" : "" %>" href="Admindashboard?page=userregister.jsp">
    <i class="bi bi-person-plus"></i> User Register
</a>

<a class="<%= "userdetails.jsp".equals(currentPage) ? "active-link" : "" %>" href="Fetch?page=userdetails.jsp&filter=all">
    <i class="bi bi-people"></i> User Details
</a>

<a class="<%= "viewevents.jsp".equals(currentPage) ? "active-link" : "" %>" href="ViewEventsAdmin">
    <i class="bi bi-calendar-event"></i> Events
</a>

<a class="<%= "adminpayments.jsp".equals(currentPage) ? "active-link" : "" %>" href="Admindashboard?page=adminpayments.jsp">
    <i class="bi bi-clock-history"></i> Payments
</a>

<a href="#" onclick="confirmLogout(event)">Logout</a>

</div>

<!-- Main Content -->
<div class="col-md-9 col-lg-10 content">

<h2>Welcome ${sessionScope.user.username}</h2>
<p class="subtitle">Manage your gym system here</p>

<!-- 🔥 DYNAMIC CARDS -->
<div class="row mt-3 g-3">

<% if("adminpayments.jsp".equals(currentPage)) { %>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Total Revenue</h5>
            <h3>₹${sessionScope.totalRevenue != null ? sessionScope.totalRevenue : 0}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Today's Revenue</h5>
            <h3>₹${sessionScope.todayRevenue != null ? sessionScope.todayRevenue : 0}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Total Transactions</h5>
            <h3>${sessionScope.totalTransactions != null ? sessionScope.totalTransactions : 0}</h3>
        </div>
    </div>

<% } else if("userdetails.jsp".equals(currentPage)) { %>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Total Users</h5>
            <h3>${sessionScope.totalUsers}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Active Members</h5>
            <h3>${sessionScope.activeMembers}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Expired Members</h5>
            <h3>${sessionScope.expiredMembers}</h3>
        </div>
    </div>

<% }   else { %>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Total Users</h5>
            <h3>${sessionScope.totalUsers}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Active Members</h5>
            <h3>${sessionScope.activeMembers}</h3>
        </div>
    </div>

    <div class="col-md-4">
        <div class="stat-card">
            <h5>Upcoming Events</h5>
            <h3>${sessionScope.eventsCount}</h3>
        </div>
    </div>

<% } %>

</div>

<!-- CONTENT PANEL -->
<div class="panel mt-3">

<%
if(currentPage != null && !currentPage.trim().isEmpty()){
%>
    <jsp:include page="<%= currentPage %>" />
<%
} else {
%>
    <p>Select an option from sidebar</p>
<%
}
%>

</div>

</div>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
function confirmLogout(e) {
    e.preventDefault();

    Swal.fire({
        title: 'Are you sure?',
        text: "You will be logged out!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = "Logout";
        }
    });
}
</script>

</body>
</html>