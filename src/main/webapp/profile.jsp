<%@ page contentType="text/html; charset=UTF-8" %>

<%
com.gym.model.User sUser = (com.gym.model.User) session.getAttribute("user");

if (sUser == null) {
    response.sendRedirect("login.html");
    return;
}

String picPath = "images/default.png";
String dobValue = "", dojValue = "";
int age = 0;
double bmi = 0;
String bmiStatus = "-";

// PROFILE PIC
if (sUser.getProfilePic() != null && !sUser.getProfilePic().isBlank()) {
    if (!"default.png".equalsIgnoreCase(sUser.getProfilePic())) {
        picPath = "uploads/" + sUser.getProfilePic();
    }
}

// DOB + AGE
try {
    if (sUser.getDob() != null) {
        java.time.LocalDate dob = sUser.getDob().toLocalDate();
        dobValue = dob.toString();
        age = java.time.Period.between(dob, java.time.LocalDate.now()).getYears();
    }
} catch (Exception e) {}

// DOJ
try {
    if (sUser.getDoj() != null) {
        dojValue = sUser.getDoj().toLocalDate().toString();
    }
} catch (Exception e) {}

// BMI
try {
    if (sUser.getHeight() > 0 && sUser.getWeight() > 0) {
        double h = sUser.getHeight() / 100.0;
        bmi = sUser.getWeight() / (h * h);

        if (bmi < 18.5) bmiStatus = "Underweight";
        else if (bmi < 25) bmiStatus = "Normal";
        else if (bmi < 30) bmiStatus = "Overweight";
        else bmiStatus = "Obese";
    }
} catch (Exception e) {}
%>

<div class="profile-container">

<!-- PROFILE CARD -->
<div class="profile-card">

    <div class="text-end">
        <button onclick="toggleEdit()" class="btn btn-outline-info btn-sm">Edit</button>
    </div>

    <div class="text-center">
        <img id="profilePreview" src="<%= picPath %>" class="avatar">
        <h3 class="mt-2">${sessionScope.user.username}</h3>
        <small class="text-muted">Member since: <%= dojValue %></small>
    </div>

    <!-- STATS -->
    <div class="stats-row">
        <div><span>Age</span><b><%= age %></b></div>
        <div><span>Weight</span><b>${sessionScope.user.weight} kg</b></div>
        <div><span>Height</span><b>${sessionScope.user.height} cm</b></div>
    </div>

    <!-- BMI -->
    <div class="bmi-box">
        <h5>BMI: <%= bmi > 0 ? String.format("%.1f", bmi) : "-" %></h5>
        <p class="bmi <%= bmiStatus.toLowerCase() %>"><%= bmiStatus %></p>
    </div>

</div>

<!-- EDIT FORM -->
<div id="editForm" class="edit-card" style="display:none;">

<form action="UpdateProfile" method="post" enctype="multipart/form-data">

<input type="text" name="username"
value="${sessionScope.user.username}"
placeholder="Username"
class="form-control mb-3" required>

<input type="date" name="dob"
value="<%= dobValue %>"
class="form-control mb-3">

<input type="text" name="phone"
value="${sessionScope.user.phone}"
placeholder="Phone"
class="form-control mb-3">

<input type="number" name="weight"
value="${sessionScope.user.weight}"
placeholder="Weight (kg)"
class="form-control mb-3">

<input type="number" name="height"
value="${sessionScope.user.height}"
placeholder="Height (cm)"
class="form-control mb-3">

<textarea name="address"
placeholder="Address"
class="form-control mb-3">${sessionScope.user.address}</textarea>

<!-- IMAGE -->
<input type="file" name="profilePic"
class="form-control mb-2"
accept="image/*"
onchange="previewImage(event)">

<div class="form-check mb-3">
<input type="checkbox" name="removePic" class="form-check-input">
<label class="form-check-label text-light">Remove profile picture</label>
</div>

<!-- PASSWORD -->
<input type="password" name="currentPassword"
placeholder="Current password"
class="form-control mb-2">

<input type="password" name="newPassword"
placeholder="New password"
class="form-control mb-2">

<input type="password" name="confirmPassword"
placeholder="Confirm password"
class="form-control mb-3">

<button class="btn btn-success w-100">Update Profile</button>

<button type="button" onclick="toggleEdit()"
class="btn btn-secondary w-100 mt-2">Cancel</button>

</form>
</div>

</div>

<!-- STYLE -->
<style>

.profile-container {
    max-width: 600px;
    margin: auto;
}

/* CARD */
.profile-card, .edit-card {
    background: rgba(15,23,42,0.85);
    padding: 25px;
    border-radius: 15px;
    margin-bottom: 20px;
}

/* IMAGE */
.avatar {
    width: 95px;
    height: 95px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid #38bdf8;
}

/* STATS */
.stats-row {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.stats-row div {
    text-align: center;
}

.stats-row span {
    display: block;
    color: #94a3b8;
    font-size: 12px;
}

.stats-row b {
    color: white;
}

/* BMI */
.bmi-box {
    text-align: center;
    margin-top: 20px;
}

.bmi {
    font-weight: bold;
}

.bmi.underweight { color:#38bdf8; }
.bmi.normal { color:#22c55e; }
.bmi.overweight { color:#f59e0b; }
.bmi.obese { color:#ef4444; }

/* INPUT FIX */
.form-control {
    background: #ffffff !important;
    color: #000 !important;
    border-radius: 10px;
}

</style>

<!-- SCRIPT -->
<script>
function toggleEdit(){
    let f = document.getElementById("editForm");
    f.style.display = (f.style.display === "none") ? "block" : "none";
}

function previewImage(event){
    const file = event.target.files[0];
    if(file){
        const reader = new FileReader();
        reader.onload = function(e){
            document.getElementById("profilePreview").src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
}
</script>