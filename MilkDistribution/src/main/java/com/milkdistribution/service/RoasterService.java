package com.milkdistribution.service;

import java.util.Calendar;

import com.milkdistribution.entity.User;

public interface RoasterService {
	
	void generateRoaster() throws Exception;
	
	void saveRoaster(User user);
	
	void saveRoaster(User user,Calendar calendar);

}
