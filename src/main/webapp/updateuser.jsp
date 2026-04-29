<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gym.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Update User</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
    background: linear-gradient(145deg,#050b18,#0b1220,#111827);
    color: #e5e7eb;
}
.page-wrap {
    max-width: 900px;
    margin: 20px auto;
}
.form-card {
    border-radius: 18px;
    padding: 25px;
    background: rgba(15,23,42,0.9);
    border: 1px solid rgba(148,163,184,0.25);
}
label {
    font-weight: 600;
    margin-bottom: 5px;
}
.form-control, .form-select {
    background: rgba(2,6,23,0.6);
    border: 1px solid rgba(148,163,184,0.3);
    color: #fff;
    border-radius: 10px;
}
.btn-update {
    background: linear-gradient(120deg,#2563eb,#4f46e5);
    border: none;
    font-weight: 700;
}
</style>
</head>

<body>

<%
User user = (User) request.getAttribute("user");
if(user == null){
%>
<div class="container text-center mt-5">
    <h4>User not found</h4>
    <a href="Fetch" class="btn btn-light mt-3">Back</a>
</div>
<%
} else {
%>

<div class="page-wrap">
<div class="form-card">

<h3 class="text-center mb-4">Update User</h3>

<form action="Update" method="post">

<input type="hidden" name="email" value="<%= user.getEmail() %>">

<div class="row g-3">

<div class="col-md-6">
<label>Username</label>
<input type="text" class="form-control" name="username"
value="<%= user.getUsername() %>" required>
</div>

<div class="col-md-6">
<label>Email</label>
<input type="email" class="form-control"
value="<%= user.getEmail() %>" readonly>
</div>

<div class="col-md-6">
<label>Password</label>
<input type="password" class="form-control"
name="password" placeholder="Enter new password" value="<%= user.getPassword() %>" required>
</div>


<div class="col-md-6">
<label>DOB</label>
<input type="date" class="form-control" name="dob"
value="<%= user.getDob() != null ? user.getDob().toLocalDate() : "" %>">
</div>

<div class="col-md-6">
<label>Gender</label>
<select class="form-select" name="gender">
<option value="">Select</option>
<option value="Male" <%= "Male".equals(user.getGender())?"selected":"" %>>Male</option>
<option value="Female" <%= "Female".equals(user.getGender())?"selected":"" %>>Female</option>
<option value="Other" <%= "Other".equals(user.getGender())?"selected":"" %>>Other</option>
</select>
</div>

<div class="col-md-6">
<label>Phone</label>
<input type="text" class="form-control" name="phone"
value="<%= user.getPhone()!=null ? user.getPhone() : "" %>">
</div>

<div class="col-md-6">
<label>Weight (kg)</label>
<input type="number" class="form-control" name="weight"
value="<%= user.getWeight()==0 ? "" : user.getWeight() %>">
</div>

<div class="col-md-6">
<label>Height (cm)</label>
<input type="number" class="form-control" name="height"
value="<%= user.getHeight()==0 ? "" : user.getHeight() %>">
</div>

<div class="col-md-6">
<label>Date of Joining</label>
<input type="date" class="form-control" name="doj"
value="<%= user.getDoj() != null ? user.getDoj().toLocalDate() : "" %>">
</div>

<div class="col-12">
<label>Address</label>
<textarea class="form-control" name="address"><%= user.getAddress()!=null ? user.getAddress() : "" %></textarea>
</div>

</div>

<button class="btn btn-update w-100 mt-4 text-white">Update</button>

</form>

</div>
</div>

<%
}
%>

</body>
</html>