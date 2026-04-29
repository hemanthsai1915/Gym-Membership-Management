package com.gym.model;

import java.util.List;

public interface EventDesign {
	public void insertEvent(EventData event);
	public void deleteEvent(int id);
	public boolean countNotification();
	public List<EventData> viewEvent(String filter);
	public void resetNotification();
	public int Eventcount();
}