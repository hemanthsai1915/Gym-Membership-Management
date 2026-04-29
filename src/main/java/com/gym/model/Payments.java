package com.gym.model;

public class Payments {
	private int id;
	private int userId;
	private int amount;
	private String paymentId;
	private String orderId;
	private String status;
	private String createdAt;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Backward-compatible accessors used by existing JSP/servlet code.
	public int getUser_id() {
		return getUserId();
	}

	public void setUser_id(int userId) {
		setUserId(userId);
	}

	public String getPayment_date() {
		return getCreatedAt();
	}

	public void setPayment_date(String createdAt) {
		setCreatedAt(createdAt);
	}

	public String getPayment_status() {
		return getStatus();
	}

	public void setPayment_status(String status) {
		setStatus(status);
	}

	public String getPayment_method() {
		return "RAZORPAY";
	}

	public void setPayment_method(String paymentMethod) {
		// no-op: retained for compatibility with legacy calls
	}
}
