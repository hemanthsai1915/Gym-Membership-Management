<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%
    boolean isEmbedded = request.getParameter("embed") != null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>User Registration</title>

<% if(!isEmbedded){ %>
<!-- ✅ Only load Bootstrap in standalone -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<% } %>

<style>

/* ===== WRAPPER ===== */
.register-wrapper{
    display:flex;
    justify-content:center;
    align-items:center;
    padding:20px;
}

/* 🔥 Standalone mode only */
<% if(!isEmbedded){ %>
.register-wrapper{
    min-height:100vh;
    background:url('login.jpg') no-repeat center/cover;
    position:relative;
}

.register-wrapper::before{
    content:"";
    position:absolute;
    inset:0;
    background:rgba(0,0,0,0.65);
}
<% } %>

/* ===== CARD ===== */
.form-card{
    position:relative;
    z-index:1;

    width:100%;
    max-width:640px;

    border-radius:16px;
    background:rgba(15,23,42,0.9);
    backdrop-filter:blur(10px);

    border:1px solid rgba(148,163,184,0.2);
}

/* 🔥 Scroll only when embedded */
<% if(isEmbedded){ %>
.form-card{
    max-height:90vh;
    overflow:auto;
}
<% } %>

/* ===== HEADER ===== */
.form-header{
    padding:16px;
    text-align:center;
}

.form-header h3{
    color:#fff;
    font-size:22px;
    font-weight:700;
}

.form-header p{
    color:#cbd5f5;
    font-size:13px;
}

/* ===== BODY ===== */
.form-body{
    padding:14px;
}

/* ===== GRID ===== */
.form-row{
    display:grid;
    grid-template-columns: 1fr 1fr;
    gap:12px 16px;
}

.full{grid-column: span 2;}

.small-row{
    grid-column: span 2;
    display:grid;
    grid-template-columns: repeat(4,1fr);
    gap:10px;
}

/* ===== INPUTS ===== */
.form-control,.form-select{
    height:36px;
    font-size:13px;
    padding:5px 10px;

    background:rgba(0,0,0,0.45);
    border:1px solid rgba(148,163,184,0.3);
    color:#fff;
    border-radius:6px;
}

.small{height:34px;}

textarea.form-control{height:60px;}

label{
    font-size:13px;
    color:#e2e8f0;
    margin-bottom:3px;
}

/* ===== BUTTON ===== */
.btn-success{
    height:40px;
    font-size:14px;
    margin-top:10px;

    background:linear-gradient(135deg,#1d4ed8,#4f46e5);
    border:none;
    border-radius:8px;
}

/* ===== IMAGE ===== */
.preview-img{
    width:65px;
    height:65px;
    border-radius:50%;
    border:2px solid #3b82f6;
    object-fit:cover;
}

</style>
</head>

<body>

<div class="register-wrapper">

<div class="form-card">

<div class="form-header">
<h3>Create Account</h3>
<p>Join the Fitness Journey...c</p>
</div>

<form class="form-body" action="Register" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">

<div class="form-row">

<!-- PROFILE -->
<div class="full text-center">
<img id="previewImg"
     src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
     class="preview-img">

<input type="file" name="profile_pic"
       class="form-control mt-1"
       accept="image/*"
       onchange="previewImage(event)">
</div>

<!-- BASIC -->
<div>
<label>Username</label>
<input type="text" name="username" class="form-control" required>
</div>

<div>
<label>Email</label>
<input type="email" name="email" class="form-control" required>
</div>

<div>
<label>Password</label>
<input type="password" name="password" id="pass" class="form-control" required>
</div>

<div>
<label>Confirm</label>
<input type="password" id="cpass" class="form-control" required>
</div>

<!-- SMALL -->
<div class="small-row">
<div>
<label>DOB</label>
<input type="date" name="dob" class="form-control small">
</div>

<div>
<label>Gender</label>
<select name="gender" class="form-select small">
<option>Select</option>
<option>Male</option>
<option>Female</option>
</select>
</div>

<div>
<label>Weight</label>
<input type="number" name="weight" class="form-control small">
</div>

<div>
<label>Height</label>
<input type="number" name="height" class="form-control small">
</div>
</div>

<div class="full">
<label>Phone</label>
<input type="text" name="phone" class="form-control">
</div>

<div class="full">
<label>Address</label>
<textarea name="address" class="form-control"></textarea>
</div>

</div>

<button class="btn btn-success w-100">Register</button>
<% if(!isEmbedded){ %>
<div class="text-center mt-3">
    <a href="login.html" style="color:#93c5fd; font-size:13px;">
        Already have an account? Login
    </a>
</div>
<% } %>
</form>

</div>
</div>

<script>
function previewImage(event){
    const file = event.target.files[0];
    if(file){
        const reader = new FileReader();
        reader.onload = e => document.getElementById("previewImg").src = e.target.result;
        reader.readAsDataURL(file);
    }
}

function validateForm(){
    let p = document.getElementById("pass").value;
    let c = document.getElementById("cpass").value;

    if(p !== c){
        alert("Passwords do not match");
        return false;
    }
    return true;
}
</script>

</body>
</html>