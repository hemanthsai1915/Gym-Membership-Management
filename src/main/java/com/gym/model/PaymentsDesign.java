package com.gym.model;

import java.util.Vector;

public interface PaymentsDesign {
Vector<Payments> getuserdata(String email);
Vector<Payments> getallpayments();
public void savePayment(Payments p);
public double getTotalRevenue();
public double getTodayRevenue();
public int getTotalTransactions();

}
