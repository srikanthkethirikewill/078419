package com.milkdistribution.service;

import com.milkdistribution.dto.UserDTO;
import com.milkdistribution.entity.User;


public interface UserService {
	
	void createUser(UserDTO user);
	
	void updateUser(UserDTO user);
	
	void verifyUser(UserDTO user);
	
	User validateUser(UserDTO userDTO);
	
	User forgotUserDetails(UserDTO userDTO);
	
	UserDTO getVerifiedUsers(String areaId);
	
	UserDTO getPendingUsers();
	
	User getUser(UserDTO userDTO, boolean isUserId);
}
