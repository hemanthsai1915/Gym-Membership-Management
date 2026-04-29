package com.gym.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gym.util.Myjdbc;

public interface MembershipDesign {
int MemberCount();
public Membership getMembershipByEmail(String email);
public Membership getMembershipByUserId(int userId);
public boolean hasActivePlan(int userId) ;
public void createMembership(Membership m) throws SQLException;
public int expiredcount();
	
}
