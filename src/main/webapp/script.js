function validateLogin() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    if(email === "" || password === "") {
        document.getElementById("errorMsg").innerText = "All fields are required!";
        return false;
    }

    document.getElementById("errorMsg").innerText = "";
    return true;
}