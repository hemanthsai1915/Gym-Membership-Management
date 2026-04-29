package com.gym.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;

    private Date dob;      // ✅ FIXED
    private Date doj;      // ✅ FIXED

    private String gender;
    private String phone;
    private String address;

    private int weight;
    private int height;

    private String profilePic;

    // GETTERS & SETTERS

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }
    public void setDob(Date date) {   // ✅ FIXED
        this.dob = date;
    }

    public int getAge() {
        if (dob == null) {
            return 0;
        }
        LocalDate birthDate = dob.toLocalDate();
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            return 0;
        }
        return Period.between(birthDate, today).getYears();
    }

    public Date getDoj() {
        return doj;
    }
    public void setDoj(Date doj) {   // ✅ FIXED
        this.doj = doj;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}