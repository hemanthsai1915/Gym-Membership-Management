package com.gym.model;

import java.util.Vector;

public interface ProjectDesign {
User login(String email,String password);
int register(User user);
void update();
void delete();
Vector<AdminUserRow> fetch(String filter);
int userCount();
public void updateUserProfile(User user);
public User getUserByEmail(String email);
public void updateUserByEmail(User user);
void delete(String email);

}

