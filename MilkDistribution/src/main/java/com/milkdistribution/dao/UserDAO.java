package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.User;

public interface UserDAO {
	
	void save(User user);
	
	void delete(User user);
	
	List<User> getUsers(boolean onlyAdmin);
	
	User getUser(String userId);
	
	User getUserBySequence(int seq);
	
	User getUserByUserId(String userId);
	
	User getUserByMailId(String mailId);
	
	List<User> getActiveUsers();
	
	List<User> getPendingUsers();
}
