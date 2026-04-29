<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.gym.model.MembershipView" %>

<%
String currentPage = request.getParameter("page");
if(currentPage == null) currentPage = "profile.jsp";
MembershipView membershipView = (MembershipView) request.getAttribute("membershipView");
if (membershipView == null) {
    membershipView = MembershipView.noPlan();
}
String flashMessage = (String) session.getAttribute("flashMessage");
if (flashMessage != null) {
    session.removeAttribute("flashMessage");
}

String sectionLabel = "Profile";
if("membership.jsp".equals(currentPage)) sectionLabel = "Membership";
else if("payments.jsp".equals(currentPage)) sectionLabel = "Payments";
else if("rechargeplan.jsp".equals(currentPage)) sectionLabel = "Recharge";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>User Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/user.css?v=20260423a">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
<style>
    body.gym-ui {
        font-family: "Plus Jakarta Sans", "Poppins", sans-serif;
        background: radial-gradient(circle at 10% 8%, rgba(56, 189, 248, 0.20), transparent 30%),
                    radial-gradient(circle at 90% 10%, rgba(167, 139, 250, 0.18), transparent 28%),
                    linear-gradient(145deg, #030712, #0b1220 58%, #172554);
        min-height: 100vh;
        color: #e2e8f0;
    }
    .gym-shell {
        width: 100%;
        min-height: 100vh;
        margin: 0;
        padding: 18px;
        background: linear-gradient(155deg, rgba(8, 15, 35, 0.62), rgba(14, 23, 52, 0.56));
        border: 1px solid rgba(148, 163, 184, 0.25);
        box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.06), 0 26px 56px rgba(2, 6, 23, 0.45);
    }
    .gym-side {
        background: linear-gradient(180deg, rgba(15, 23, 42, 0.90), rgba(30, 64, 175, 0.76) 42%, rgba(124, 58, 237, 0.72));
        border: 1px solid rgba(191, 219, 254, 0.45);
        border-radius: 18px;
        padding: 20px 14px;
        height: calc(100vh - 36px);
        position: sticky;
        top: 18px;
        overflow-y: auto;
    }
    .gym-logo { color: #f8fafc; text-align: center; margin-bottom: 14px; }
    .gym-logo h5 { margin-bottom: 4px; font-weight: 700; }
    .gym-logo small { color: #bfdbfe; }
    .gym-nav a {
        display: block;
        margin: 6px 0;
        padding: 10px 12px;
        border-radius: 12px;
        color: #dbeafe;
        text-decoration: none;
        font-weight: 500;
        border: 1px solid transparent;
    }
    .gym-nav a:hover { background: rgba(59, 130, 246, 0.24); color: #fff; }
    .gym-nav a.gym-active {
        background: linear-gradient(120deg, rgba(37, 99, 235, .30), rgba(16, 185, 129, .24));
        color: #fff;
        border-color: rgba(191, 219, 254, .52);
    }
    .gym-main { padding: 6px 8px; }
    .gym-hero {
        border-radius: 18px;
        padding: 18px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        background: linear-gradient(120deg, rgba(15, 23, 42, 0.86), rgba(30, 64, 175, 0.72));
        border: 1px solid rgba(96, 165, 250, 0.36);
        color: #f8fafc;
    }
    .gym-hero h4 { margin: 0; font-weight: 800; }
    .gym-hero p { margin: 2px 0 0; color: #cbd5e1; font-weight: 500; }
    .gym-chip {
        padding: 6px 10px;
        border-radius: 999px;
        font-size: 12px;
        font-weight: 600;
        color: #f8fafc;
        background: rgba(37, 99, 235, .28);
        border: 1px solid rgba(147, 197, 253, .5);
    }
    .gym-stat {
        position: relative;
        overflow: hidden;
        border-radius: 14px;
        padding: 14px;
        text-align: center;
        border: 1px solid rgba(148, 163, 184, 0.30);
        background: linear-gradient(160deg, rgba(30, 41, 59, 0.74), rgba(15, 23, 42, 0.66));
    }
    .gym-stat::before {
        content: "";
        position: absolute;
        left: 0; top: 0;
        width: 100%; height: 3px;
        background: linear-gradient(90deg, #06b6d4, #2563eb, #7c3aed);
    }
    .gym-stat .k { font-size: 11px; color: #94a3b8; text-transform: uppercase; letter-spacing: .08em; }
    .gym-stat .v { font-size: 24px; font-weight: 800; margin-top: 2px; color: #f8fafc; }
    .row.g-3.mb-3 > div:nth-child(1) .gym-stat { background: linear-gradient(130deg, rgba(30, 64, 175, .28), rgba(15, 23, 42, .68)); }
    .row.g-3.mb-3 > div:nth-child(2) .gym-stat { background: linear-gradient(130deg, rgba(30, 64, 175, .22), rgba(15, 23, 42, .68)); }
    .row.g-3.mb-3 > div:nth-child(3) .gym-stat { background: linear-gradient(130deg, rgba(16, 185, 129, .20), rgba(15, 23, 42, .68)); }
    .gym-content {
        border-radius: 16px;
        padding: 16px;
        border: 1px solid rgba(148, 163, 184, 0.30);
        background: linear-gradient(160deg, rgba(30, 41, 59, 0.72), rgba(15, 23, 42, 0.64));
        box-shadow: 0 16px 30px rgba(2, 6, 23, 0.35);
    }
    .gym-content .card-box,
    .gym-content .plan-card {
        border-radius: 14px !important;
        background: linear-gradient(155deg, rgba(30, 41, 59, 0.72), rgba(15, 23, 42, 0.64)) !important;
        color: #e2e8f0 !important;
        border: 1px solid rgba(148, 163, 184, 0.28) !important;
        box-shadow: 0 10px 24px rgba(2, 6, 23, 0.35) !important;
    }
    .gym-content,
    .gym-content h4,
    .gym-content h5,
    .gym-content h6,
    .gym-content p,
    .gym-content strong,
    .gym-content td,
    .gym-content th { color: #e2e8f0 !important; }
    .gym-content .text-muted { color: #94a3b8 !important; }
    .gym-content .table {
        --bs-table-bg: transparent;
        --bs-table-color: #e2e8f0;
        --bs-table-hover-bg: rgba(59, 130, 246, 0.1);
    }
    .gym-content .table thead th {
        color: #bfdbfe !important;
        border-bottom-color: rgba(148, 163, 184, 0.32);
        font-weight: 700;
        letter-spacing: 0.02em;
    }
    .gym-content .table td { border-bottom-color: rgba(148, 163, 184, 0.2); }
    .gym-content .badge { color: #e2e8f0 !important; border-color: rgba(148, 163, 184, 0.4) !important; }
    .gym-content .badge.bg-primary-subtle,
    .gym-content .badge.bg-secondary-subtle {
        background: rgba(30, 64, 175, 0.35) !important;
        color: #dbeafe !important;
        border-color: rgba(147, 197, 253, 0.42) !important;
    }
    .gym-content .badge.bg-warning,
    .gym-content .badge.bg-warning-subtle {
        background: rgba(245, 158, 11, 0.22) !important;
        color: #fde68a !important;
        border: 1px solid rgba(245, 158, 11, 0.38);
    }
    .gym-content .text-success,
    .gym-content .status-active {
        color: #34d399 !important;
    }
    .gym-content .text-danger,
    .gym-content .status-expired {
        color: #f87171 !important;
    }
    .gym-content .btn {
        color: #e2e8f0;
        border-color: rgba(148, 163, 184, 0.45);
        background-color: rgba(30, 41, 59, 0.55);
    }
    .gym-shell * { animation: none !important; transition: none !important; }
    .gym-shell .card-box:hover,
    .gym-shell .plan-card:hover,
    .gym-shell .gym-nav a:hover { transform: none !important; }
    @media (max-width: 767px) {
        .gym-shell { padding: 12px; }
        .gym-main { padding: 4px; }
        .gym-hero h4 { font-size: 20px; }
        .gym-side { height: auto; position: static; top: auto; overflow-y: visible; }
    }
</style>

</head>

<body class="gym-ui">

<div class="gym-shell">
    <% if (flashMessage != null) { %>
        <div class="alert alert-info mb-3" role="alert"><%= flashMessage %></div>
    <% } %>
    <div class="row g-3">

        <!-- SIDEBAR -->
        <div class="col-12 col-md-3 col-lg-2">
            <aside class="gym-side">
                <div class="gym-logo">
                    <h5>User Panel</h5>
                    <small>Elite Fitness</small>
                </div>

                <nav class="gym-nav">
                    <a class="<%= currentPage.equals("profile.jsp") ? "gym-active" : "" %>" href="UserDashboard?page=profile.jsp">Profile</a>
                    <a class="<%= currentPage.equals("membership.jsp") ? "gym-active" : "" %>" href="UserDashboard?page=membership.jsp">Membership</a>
                    <a class="<%= currentPage.equals("payments.jsp") ? "gym-active" : "" %>" href="UserDashboard?page=payments.jsp">Payments</a>
                    <a href="ViewEventsUser">
    						<i class="bi bi-calendar-event">
    				</i> Check Events
                    </a>
                    <a class="<%= currentPage.equals("rechargeplan.jsp") ? "gym-active" : "" %>" href="UserDashboard?page=rechargeplan.jsp">Membership Plans</a>
                </nav>

                <hr class="my-3 border-light border-opacity-25">
               <div class="gym-nav">
    <a href="#" onclick="confirmLogout(event)">Logout</a>
</div>
            </aside>
        </div>

        <!-- MAIN -->
        <div class="col-12 col-md-9 col-lg-10">
            <main class="gym-main">
                <div class="gym-hero mb-3">
                    <div>
                        <h4>Welcome back, ${sessionScope.user.username}</h4>
                        <p>Your gym dashboard for membership and payments</p>
                    </div>
                    <span class="gym-chip"><%= sectionLabel %></span>
                </div>

                <!-- TOP CARDS -->
                <div class="row g-3 mb-3">
                    <% if(currentPage.equals("membership.jsp")) { %>
                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Membership Status</div>
                                <div class="v"><%= membershipView.getDisplayStatus() %></div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Days Left</div>
                                <div class="v"><%= membershipView.getDaysRemaining() %></div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Current Plan</div>
                                <div class="v"><%= membershipView.hasMembership() ? membershipView.getMembership().getPlanName() : "-" %></div>
                            </div>
                        </div>
                    <% } else { %>
                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Member Name</div>
                                <div class="v">${sessionScope.user.username}</div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Email</div>
                                <div class="v">${sessionScope.email}</div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="gym-stat">
                                <div class="k">Phone</div>
                                <div class="v">${sessionScope.user.phone}</div>
                            </div>
                        </div>
                    <% } %>
                </div>

                <!-- MAIN CONTENT -->
                <section class="gym-content">
                    <jsp:include page="<%= currentPage %>" />
                </section>
            </main>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>




<script>
function pay(plan, amount, days) {

    fetch("CreateOrder?amount=" + amount)
    .then(res => res.json())
    .then(order => {

        console.log("Response:", order); // 🔍 debug

        // 🔥 🔥 ADD THIS BLOCK
        if (order.status === "ACTIVE") {
            Swal.fire({
                icon: 'info',
                title: 'Already Active',
                text: order.message,
                confirmButtonColor: '#3085d6'
            });
            return; // ❌ STOP HERE (no Razorpay)
        }

        // ✅ ONLY RUN THIS IF NOT ACTIVE
        let isHandled = false;

        var options = {
            key: "rzp_test_ShYQR0oAoEClXB",
            amount: order.amount,
            currency: "INR",
            name: "Elite Fitness",
            description: plan + " Plan",
            order_id: order.id,

            handler: function (response) {
                if(isHandled) return;
                isHandled = true;

                sendToServer({
                    razorpay_payment_id: response.razorpay_payment_id,
                    razorpay_order_id: response.razorpay_order_id,
                    razorpay_signature: response.razorpay_signature
                }, plan, amount, days, "SUCCESS");
            }
        };

        var rzp = new Razorpay(options);

        rzp.on("payment.failed", function (response) {

            if(isHandled) return;
            isHandled = true;

            sendToServer({
                razorpay_payment_id: "FAILED_" + Date.now(),
                razorpay_order_id: order.id,
                razorpay_signature: "NA"
            }, plan, amount, days, "FAILED");
        });

        rzp.open(); // ✅ now safe
    });
}


// 🔥 COMMON FUNCTION
function sendToServer(res, plan, amount, days, status) {

    fetch("VerifyPayment", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:
            "payment_id=" + encodeURIComponent(res.razorpay_payment_id) +
            "&order_id=" + encodeURIComponent(res.razorpay_order_id) +
            "&signature=" + encodeURIComponent(res.razorpay_signature) +
            "&plan=" + encodeURIComponent(plan) +
            "&amount=" + encodeURIComponent(amount) +
            "&days=" + encodeURIComponent(days) +
            "&status=" + encodeURIComponent(status)
    })
    .then(r => r.text())
    .then(msg => {

        console.log("SERVER:", msg);

        if(status === "SUCCESS"){
        	Swal.fire({
        	    icon: 'success',
        	    title: 'Payment Successful',
        	    text: 'Your membership is activated 🎉',
        	    confirmButtonColor: '#3085d6'
        	});
            window.location = "UserDashboard?page=membership.jsp";
        } else {
        	Swal.fire({
        	    icon: 'error',
        	    title: 'Payment Failed',
        	    text: 'Please try again or use another method',
        	    confirmButtonColor: '#d33'
        	});
        }
    });
}
</script>
<script>
function confirmLogout(e) {
    e.preventDefault(); // stop instant logout

    Swal.fire({
        title: 'Are you sure?',
        text: "You will be logged out!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, logout'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = "Logout";
        }
    });
}
</script>
</body>
</html>