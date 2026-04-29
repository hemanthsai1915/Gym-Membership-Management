<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<!-- HEADER -->
<div class="d-flex justify-content-between align-items-center mb-3">
    <h5 class="text-light mb-0">
        <i class="bi bi-plus-circle text-success"></i> Add Event
    </h5>

    <!-- BACK BUTTON -->
    <a href="ViewEventsAdmin" class="btn btn-outline-light btn-sm px-3 rounded-pill">
        <i class="bi bi-arrow-left"></i> Back
    </a>
</div>

<!-- FORM -->
<form action="AddEvent" method="post" class="event-form">

    <div class="form-floating mb-3">
        <input type="text" class="form-control custom-input"
               name="title" placeholder="Event Title" required>
        <label>Event Title</label>
    </div>

    <div class="form-floating mb-3">
        <textarea class="form-control custom-input"
                  name="description"
                  placeholder="Description"
                  style="height:100px" required></textarea>
        <label>Description</label>
    </div>

    <div class="mb-4">
        <label class="text-secondary small mb-1">Event Date</label>
        <input type="date" class="form-control custom-input" name="date" required>
    </div>

    <button class="btn btn-gradient w-100 py-2">
        <i class="bi bi-check-circle"></i> Create Event
    </button>

</form>

<style>
.event-form {
    background: linear-gradient(145deg, rgba(30,41,59,0.9), rgba(15,23,42,0.75));
    padding: 25px;
    border-radius: 16px;
    border: 1px solid rgba(148,163,184,0.2);
    box-shadow: 0 10px 30px rgba(0,0,0,0.5);
}

.custom-input {
    background: rgba(255,255,255,0.05);
    border: 1px solid rgba(148,163,184,0.2);
    color: white;
}

.custom-input:focus {
    border-color: #22c55e;
    box-shadow: 0 0 0 0.2rem rgba(34,197,94,0.2);
}

.btn-gradient {
    background: linear-gradient(135deg, #22c55e, #16a34a);
    border: none;
    color: white;
    font-weight: 600;
    border-radius: 25px;
    transition: 0.3s;
}
.btn-gradient:hover {
    transform: scale(1.03);
    box-shadow: 0 5px 20px rgba(34,197,94,0.4);
}
</style>