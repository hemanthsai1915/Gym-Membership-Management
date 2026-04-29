package com.gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.gym.model.Membership;
import com.gym.model.MembershipDesign;
import com.gym.model.MembershipView;
import com.gym.util.Myjdbc;

public class MembershipDAO implements MembershipDesign {

	private static final String ACTIVE_PLAN_QUERY = "SELECT 1 FROM membership "
			+ "WHERE user_id = ? AND status = 'ACTIVE' AND start_date <= CURRENT_DATE AND end_date >= CURRENT_DATE LIMIT 1";

	@Override
	public boolean hasActivePlan(int userId) {
		if (userId <= 0) {
			return false;
		}
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(ACTIVE_PLAN_QUERY)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void createMembership(Membership m) throws SQLException {
		if (m == null) {
			return;
		}
		String sql = "INSERT INTO membership(user_id, plan_name, start_date, end_date, status) VALUES(?,?,?,?,?)";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, m.getUser_id());
			ps.setString(2, m.getPlan_name());
			ps.setDate(3, m.getStart_date());
			ps.setDate(4, m.getEnd_date());
			ps.setString(5, m.getStatus());
			ps.executeUpdate();
		}
	}

	@Override
	public int MemberCount() {
		String sql = "SELECT COUNT(DISTINCT m.user_id) FROM membership m "
				+ "JOIN gym_users u ON u.id = m.user_id "
				+ "WHERE u.email <> 'admin@gmail.com' "
				+ "AND m.start_date <= CURRENT_DATE AND m.end_date >= CURRENT_DATE";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			return rs.next() ? rs.getInt(1) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int expiredcount() {
		String sql = "SELECT COUNT(DISTINCT m.user_id) FROM membership m "
				+ "JOIN gym_users u ON u.id = m.user_id "
				+ "WHERE u.email <> 'admin@gmail.com' "
				+ "AND m.end_date <= CURRENT_DATE";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			return rs.next() ? rs.getInt(1) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Membership getMembershipByEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			return null;
		}
		String sql = "SELECT m.id, m.user_id, m.plan_name, m.start_date, m.end_date, m.status "
				+ "FROM membership m JOIN gym_users u ON m.user_id = u.id "
				+ "WHERE u.email = ? ORDER BY CASE WHEN m.status = 'ACTIVE' THEN 0 ELSE 1 END, m.end_date DESC LIMIT 1";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapMembership(rs) : null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Membership getMembershipByUserId(int userId) {
		if (userId <= 0) {
			return null;
		}
		String sql = "SELECT id, user_id, plan_name, start_date, end_date, status FROM membership "
				+ "WHERE user_id = ? "
				+ "ORDER BY CASE WHEN status = 'ACTIVE' THEN 0 ELSE 1 END, end_date DESC LIMIT 1";
		try (Connection con = Myjdbc.myconn();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapMembership(rs) : null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public MembershipView getMembershipView(int userId) {
		Membership membership = getMembershipByUserId(userId);
		if (membership == null || membership.getStart_date() == null || membership.getEnd_date() == null) {
			return MembershipView.noPlan();
		}

		LocalDate today = LocalDate.now();
		LocalDate start = membership.getStart_date().toLocalDate();
		LocalDate end = membership.getEnd_date().toLocalDate();
		long daysRemaining = ChronoUnit.DAYS.between(today, end);
		long totalDays = Math.max(1, ChronoUnit.DAYS.between(start, end));
		boolean activeByDate = !today.isBefore(start) && !today.isAfter(end);

		String normalized = membership.getStatus() == null ? "" : membership.getStatus().trim().toUpperCase();
		String finalStatus = activeByDate ? "ACTIVE" : "EXPIRED";
		if (normalized.isEmpty() || "ACTIVE".equals(normalized) || "EXPIRED".equals(normalized)) {
			normalized = finalStatus;
		}

		int progressPercent = 0;
		if (daysRemaining > 0) {
			progressPercent = (int) Math.min(100, Math.max(0, (daysRemaining * 100) / totalDays));
		}
		return new MembershipView(membership, normalized, Math.max(daysRemaining, 0), progressPercent);
	}

	private Membership mapMembership(ResultSet rs) throws SQLException {
		Membership membership = new Membership();
		membership.setId(rs.getInt("id"));
		membership.setUser_id(rs.getInt("user_id"));
		membership.setPlan_name(rs.getString("plan_name"));
		membership.setStart_date(rs.getDate("start_date"));
		membership.setEnd_date(rs.getDate("end_date"));
		membership.setStatus(rs.getString("status"));
		return membership;
	}
}
