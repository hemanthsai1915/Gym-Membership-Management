<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Vector, com.gym.model.AdminUserRow" %>

<div class="container">

<h3 class="mb-3">User Details</h3>

<!-- FILTER + EXPORT -->
<div class="d-flex justify-content-between mb-3">

    <div>
        <a href="Fetch?filter=all" class="btn btn-secondary btn-sm">All</a>
        <a href="Fetch?filter=active" class="btn btn-success btn-sm">Active</a>
        <a href="Fetch?filter=expired" class="btn btn-danger btn-sm">Expired</a>
        <a href="Fetch?filter=noplan" class="btn btn-warning btn-sm">No Plan</a>
    </div>

    <a href="ExportUsers?filter=<%= request.getAttribute("selectedFilter") %>"
       class="btn btn-success btn-sm">
       ⬇ Export Users
    </a>

</div>

<!-- SEARCH -->
<input type="text" id="searchInput"
       class="form-control mb-3"
       placeholder="Search users...">

<table class="table table-bordered table-hover">

<tr class="table-dark">
    <th>Username</th>
    <th>Email</th>
    <th>Phone</th>
    <th>Plan</th>
    <th>Start</th>
    <th>End</th>
    <th>Status</th>
    <th>Days Left</th>
    <th>Actions</th>
</tr>

<tbody id="userTable">

<%
Vector<AdminUserRow> users = (Vector<AdminUserRow>) request.getAttribute("users");

if(users != null){
for(AdminUserRow u : users){

String st = (u.getMembershipStatus() == null) ? "NO PLAN" : u.getMembershipStatus();
String badgeCls = "bg-secondary";

if("ACTIVE".equalsIgnoreCase(st)) badgeCls = "bg-success";
else if("EXPIRED".equalsIgnoreCase(st)) badgeCls = "bg-danger";
%>

<tr data-status="<%= st %>">

<td><%= u.getUsername() %></td>
<td><%= u.getEmail() %></td>
<td><%= u.getPhone() %></td>

<td><%= (u.getPlanName()==null)?"-":u.getPlanName() %></td>
<td><%= (u.getStartDate()==null)?"-":u.getStartDate() %></td>
<td><%= (u.getEndDate()==null)?"-":u.getEndDate() %></td>

<td>
<span class="badge <%= badgeCls %>"><%= st %></span>
</td>

<td><%= "NO PLAN".equals(st) ? "-" : u.getDaysLeft() %></td>

<td>
<form id="deleteForm_<%= u.getEmail() %>" action="Delete" method="post" style="display:inline;">
<input type="hidden" name="email" value="<%= u.getEmail() %>">
<button type="button"
onclick="confirmDelete('<%= u.getEmail() %>')"
class="btn btn-danger btn-sm">Delete</button>
</form>

<form action="Update" method="get" style="display:inline;">
<input type="hidden" name="email" value="<%= u.getEmail() %>">
<button class="btn btn-warning btn-sm">Edit</button>
</form>
</td>

</tr>

<%
}
}
%>

</tbody>
</table>
</div>

<!-- SWEET ALERT -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
// DELETE
function confirmDelete(email){
    Swal.fire({
        title: 'Delete user?',
        icon: 'warning',
        showCancelButton: true
    }).then((r)=>{
        if(r.isConfirmed){
            document.getElementById("deleteForm_"+email).submit();
        }
    });
}

// SEARCH
document.getElementById("searchInput").addEventListener("keyup", function(){
    let val = this.value.toLowerCase();
    let rows = document.querySelectorAll("#userTable tr");

    rows.forEach(r=>{
        let text = r.innerText.toLowerCase();
        r.style.display = text.includes(val) ? "" : "none";
    });
});

// FRONTEND FILTER (OPTIONAL NO RELOAD)
function filterUsers(type){

    let rows = document.querySelectorAll("#userTable tr");

    rows.forEach(row=>{
        let status = row.getAttribute("data-status");

        if(type==="all") row.style.display="";
        else if(type==="active" && status==="ACTIVE") row.style.display="";
        else if(type==="expired" && status==="EXPIRED") row.style.display="";
        else if(type==="noplan" && status==="NO PLAN") row.style.display="";
        else row.style.display="none";
    });
}
</script>