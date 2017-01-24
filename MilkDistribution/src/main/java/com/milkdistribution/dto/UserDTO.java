package com.milkdistribution.dto;

import java.util.List;

import com.milkdistribution.entity.User;

public class UserDTO {
	
	private User user;
	
	private int seq;
	
	private String roasterUpdateRequired;
	
	private List<User> users;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getRoasterUpdateRequired() {
		return roasterUpdateRequired;
	}

	public void setRoasterUpdateRequired(String roasterUpdateRequired) {
		this.roasterUpdateRequired = roasterUpdateRequired;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}	
	
}
