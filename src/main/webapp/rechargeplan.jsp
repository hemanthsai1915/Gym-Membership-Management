<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
    <h4 class="mb-0 fw-bold">Choose Your Plan</h4>
    <span class="badge bg-primary-subtle text-primary">Recharge</span>
</div>

<!-- FILTER -->
<div class="mb-3 d-flex flex-wrap gap-2">
    <button class="btn btn-outline-primary btn-sm" onclick="filter('all')">All</button>
    <button class="btn btn-outline-secondary btn-sm" onclick="filter('monthly')">Monthly</button>
    <button class="btn btn-outline-secondary btn-sm" onclick="filter('quarterly')">Quarterly</button>
    <button class="btn btn-outline-secondary btn-sm" onclick="filter('half')">Half-Year</button>
    <button class="btn btn-outline-secondary btn-sm" onclick="filter('annual')">Annual</button>
</div>

<div class="row g-4">

    <!-- MONTHLY -->
    <div class="col-md-4 plan monthly">
        <div class="plan-card">
            <h6>Monthly</h6>
            <div class="price text-warning">₹1499</div>
            <p>30 Days</p>
            <button class="btn btn-warning w-100 buy-btn" onclick="pay('Monthly',1500,30)">Buy</button>
           
        </div>
    </div>

    <!-- QUARTERLY (POPULAR) -->
    <div class="col-md-4 plan quarterly">
        <div class="plan-card plan-popular">
            <div class="tag">Best Value</div>
            <h6>Quarterly</h6>
            <div class="price text-primary">₹3999</div>
            <p>90 Days</p>
            <button class="btn btn-primary w-100 buy-btn" onclick="pay('Quarterly',4000,90)">Buy</button>
           
        </div>
    </div>

    <!-- HALF -->
    <div class="col-md-4 plan half">
        <div class="plan-card">
            <h6>Half-Year</h6>
            <div class="price text-info">₹7499</div>
            <p>180 Days</p>
            <button class="btn btn-info w-100 buy-btn" onclick="pay('Half-Year',7500,180)">Buy</button>
       
        </div>
    </div>

    <!-- ANNUAL -->
    <div class="col-md-4 plan annual">
        <div class="plan-card">
            <h6>Annual</h6>
            <div class="price text-success">₹9999</div>
            <p>365 Days</p>
            <button class="btn btn-success w-100 buy-btn" onclick="pay('Annual',10000,365)">Buy</button>
            
        </div>
    </div>

</div>

<!-- FILTER SCRIPT -->
<script>
function filter(type){
    let plans = document.querySelectorAll(".plan");

    plans.forEach(p => {
        if(type === "all" || p.classList.contains(type)){
            p.style.display = "block";
        } else {
            p.style.display = "none";
        }
    });
}
</script>

<!-- Razorpay -->
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

