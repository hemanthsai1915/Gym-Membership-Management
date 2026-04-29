package com.gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.gym.model.Payments;
import com.gym.model.PaymentsDesign;
import com.gym.util.Myjdbc;

public class PaymentsDAO implements PaymentsDesign {

	@Override
	public Vector<Payments> getuserdata(String email) {
		Vector<Payments> payments = new Vector<>();
		if (email == null || email.trim().isEmpty()) {
			return payments;
		}
		String sql = "SELECT p.id, p.user_id, p.amount, p.payment_id, p.order_id, p.status, p.created_at, u.email "
				+ "FROM payments p JOIN gym_users u ON p.user_id = u.id WHERE u.email = ? ORDER BY p.id DESC";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				mapPayments(rs, payments);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public void savePayment(Payments payment) {
		if (payment == null) {
			return;
		}
		String sql = "INSERT INTO payments(user_id, amount, payment_id, order_id, status) VALUES(?,?,?,?,?)";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, payment.getUserId());
			ps.setInt(2, payment.getAmount());
			ps.setString(3, payment.getPaymentId());
			ps.setString(4, payment.getOrderId());
			ps.setString(5, payment.getStatus());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<Payments> getuserdataByUserId(int userId) {
		Vector<Payments> payments = new Vector<>();
		if (userId <= 0) {
			return payments;
		}
		String sql = "SELECT id, user_id, amount, payment_id, order_id, status, created_at FROM payments "
				+ "WHERE user_id = ? ORDER BY id DESC";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				mapPayments(rs, payments);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payments;
	}

	@Override
	public Vector<Payments> getallpayments() {
		Vector<Payments> payments = new Vector<>();
		String sql = "SELECT p.id, p.user_id, p.amount, p.payment_id, p.order_id, p.status, p.created_at, u.email"+
				" FROM payments p "+
				"LEFT JOIN gym_users u ON p.user_id = u.id"+ 
				" WHERE p.status = 'SUCCESS'"
				+" ORDER BY p.id DESC;";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			mapPayments(rs, payments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payments;
	}

	private void mapPayments(ResultSet rs, Vector<Payments> payments) throws Exception {
		while (rs.next()) {
			Payments payment = new Payments();
			payment.setId(rs.getInt("id"));
			payment.setUserId(rs.getInt("user_id"));
			payment.setAmount(rs.getInt("amount"));
			payment.setPaymentId(rs.getString("payment_id"));
			payment.setOrderId(rs.getString("order_id"));
			payment.setStatus(rs.getString("status"));
			payment.setCreatedAt(rs.getString("created_at"));
			try {
				payment.setEmail(rs.getString("email"));
			} catch (Exception ignored) {
				payment.setEmail(null);
			}
			payments.add(payment);
		}
	}
	@Override
	public double getTotalRevenue() {
	    double total = 0;

	    try (Connection con = Myjdbc.myconn();
	         PreparedStatement ps = con.prepareStatement(
	             "SELECT SUM(amount) FROM payments WHERE status='SUCCESS'"
	         );
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            total = rs.getDouble(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return total;
	}
	@Override
	public double getTodayRevenue() {
	    double total = 0;

	    try (Connection con = Myjdbc.myconn();
	         PreparedStatement ps = con.prepareStatement(
	             "SELECT SUM(amount) FROM payments WHERE DATE(created_at)=CURDATE() AND status='SUCCESS'"
	         );
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            total = rs.getDouble(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return total;
	}
	@Override
	public int getTotalTransactions() {
	    int count = 0;

	    try (Connection con = Myjdbc.myconn();
	         PreparedStatement ps = con.prepareStatement(
	             "SELECT COUNT(*) FROM payments WHERE status='SUCCESS'"
	         );
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count;
	}
}

