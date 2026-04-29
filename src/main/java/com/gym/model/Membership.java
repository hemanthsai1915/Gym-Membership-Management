package com.gym.model;

import java.sql.Date;

public class Membership {
	private int id;
	private int userId;
	private String planName;
	private Date startDate;
	private Date endDate;
	private String status;

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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Backward-compatible accessors used by existing JSP/servlet code.
	public int getUser_id() {
		return getUserId();
	}

	public void setUser_id(int userId) {
		setUserId(userId);
	}

	public String getPlan_name() {
		return getPlanName();
	}

	public void setPlan_name(String planName) {
		setPlanName(planName);
	}

	public Date getStart_date() {
		return getStartDate();
	}

	public void setStart_date(Date startDate) {
		setStartDate(startDate);
	}

	public Date getEnd_date() {
		return getEndDate();
	}

	public void setEnd_date(Date endDate) {
		setEndDate(endDate);
	}
}
