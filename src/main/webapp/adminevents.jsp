<%@ page import="java.util.*, com.gym.model.EventData" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<div class="card-box">

    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="text-light mb-0">
            <i class="bi bi-calendar-event text-info"></i> Manage Events
        </h5>

        <a href="AddEvent" class="btn btn-gradient btn-sm px-3">
            <i class="bi bi-plus-lg"></i> Add Event
        </a>
    </div>

    <!-- 🔥 FILTER BUTTONS -->
    <div class="mb-3 d-flex gap-2 flex-wrap">

        <a href="ViewEventsAdmin?filter=all" class="btn btn-outline-light btn-sm">All</a>

        <a href="ViewEventsAdmin?filter=upcoming" class="btn btn-outline-info btn-sm">Upcoming</a>

        <a href="ViewEventsAdmin?filter=today" class="btn btn-outline-success btn-sm">Today</a>

        <a href="ViewEventsAdmin?filter=past" class="btn btn-outline-secondary btn-sm">Past</a>

    </div>

    <%
    List<EventData> events = (List<EventData>) request.getAttribute("events");

    if(events == null || events.isEmpty()){
    %>

    <!-- EMPTY STATE -->
    <div class="empty-box text-center">
        <i class="bi bi-calendar-x fs-2"></i>
        <p class="mt-2 mb-0">No events found</p>
    </div>

    <%
    } else {
        for(EventData e : events){
    %>

    <!-- EVENT CARD -->
    <div class="event-card p-3 mb-3">

        <div class="d-flex justify-content-between align-items-center">

            <div>
                <h6 class="mb-1 text-white fw-semibold">
                    <i class="bi bi-stars text-warning"></i>
                    <%= e.getTitle() %>
                </h6>

                <p class="text-secondary small mb-2">
                    <%= e.getDescription() %>
                </p>

                <span class="badge bg-info text-dark px-3 py-2">
                    <i class="bi bi-calendar"></i> <%= e.getDate() %>
                </span>
            </div>

            <!-- DELETE -->
            <form id="deleteForm_<%= e.getId() %>" action="DeleteEvent" method="post">
                <input type="hidden" name="id" value="<%= e.getId() %>">

                <button type="button"
                        onclick="confirmDelete(<%= e.getId() %>)"
                        class="btn btn-danger btn-sm rounded-pill px-3">
                    <i class="bi bi-trash"></i>
                </button>
            </form>

        </div>

    </div>

    <%
        }
    }
    %>

</div>

<style>
.btn-gradient {
    background: linear-gradient(135deg, #22c55e, #16a34a);
    border: none;
    color: white;
    font-weight: 600;
    border-radius: 20px;
    transition: 0.3s;
}
.btn-gradient:hover {
    transform: scale(1.05);
    box-shadow: 0 5px 15px rgba(34,197,94,0.4);
}

.event-card {
    border-radius: 16px;
    background: linear-gradient(145deg, rgba(30,41,59,0.9), rgba(15,23,42,0.75));
    border: 1px solid rgba(148,163,184,0.2);
    backdrop-filter: blur(12px);
    box-shadow: 0 10px 30px rgba(0,0,0,0.5);
    transition: 0.3s ease;
}
.event-card:hover {
    transform: translateY(-5px);
    border-color: rgba(59,130,246,0.5);
}

.empty-box {
    border: 1px dashed rgba(148,163,184,0.4);
    border-radius: 12px;
    padding: 40px;
    color: #94a3b8;
}
</style>

<script>
function confirmDelete(id){
    Swal.fire({
        title: 'Delete Event?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef4444',
        confirmButtonText: 'Delete'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("deleteForm_" + id).submit();
        }
    });
}
</script>