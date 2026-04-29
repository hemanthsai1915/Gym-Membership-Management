<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.gym.model.EventData" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Gym Events</title>

<!-- Bootstrap + Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

<style>
body {
    background: linear-gradient(145deg, #0b1220, #0f172a 60%, #111827);
    color: #e2e8f0;
}

/* Container */
.events-container {
    max-width: 800px;
    margin: auto;
    margin-top: 50px;
}

/* Title */
.title {
    font-weight: 800;
    text-align: center;
    margin-bottom: 30px;
}

/* Event Card */
.event-card {
    border-radius: 16px;
    padding: 18px;
    margin-bottom: 15px;
    background: linear-gradient(145deg, rgba(30,41,59,0.9), rgba(15,23,42,0.7));
    border: 1px solid rgba(148,163,184,0.2);
    box-shadow: 0 10px 30px rgba(0,0,0,0.5);
    transition: 0.3s;
}

.event-card:hover {
    transform: translateY(-4px);
    border-color: rgba(59,130,246,0.5);
}

/* Date Badge */
.date-badge {
    background: #0ea5e9;
    color: black;
    padding: 5px 12px;
    border-radius: 20px;
    font-size: 12px;
    font-weight: 600;
}

/* Empty State */
.empty-box {
    border: 1px dashed rgba(148,163,184,0.4);
    border-radius: 12px;
    padding: 40px;
    text-align: center;
    color: #94a3b8;
}

/* Back Button */
.back-btn {
    border-radius: 25px;
    padding: 6px 20px;
}
</style>

</head>

<body>

<div class="events-container">

    <!-- TITLE -->
    <h3 class="title">
        <i class="bi bi-calendar-event text-info"></i> Upcoming Events
    </h3>

    <%
        List<EventData> events = (List<EventData>) request.getAttribute("events");

        if(events == null || events.isEmpty()){
    %>

    <!-- EMPTY -->
    <div class="empty-box">
        <i class="bi bi-calendar-x fs-2"></i>
        <p class="mt-2 mb-0">No events available right now</p>
    </div>

    <%
        } else {
            for(EventData e : events){
    %>

    <!-- EVENT CARD -->
    <div class="event-card">

        <h5 class="mb-1 text-white fw-semibold">
            <i class="bi bi-stars text-warning"></i>
            <%= e.getTitle() %>
        </h5>

        <p class="text-secondary small mb-2">
            <%= e.getDescription() %>
        </p>

        <span class="date-badge">
            <i class="bi bi-calendar"></i> <%= e.getDate() %>
        </span>

    </div>

    <%
            }
        }
    %>

    <!-- BACK -->
    <div class="text-center mt-4">
        <a href="UserDashboard" class="btn btn-outline-light back-btn">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
    </div>

</div>

</body>
</html>