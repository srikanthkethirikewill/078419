package com.milkdistribution.service;

import java.util.Calendar;
import java.util.List;

import com.milkdistribution.dto.RoasterDTO;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;

public interface RoasterService {
	
	void generateRoaster() throws Exception;
	
	void saveRoaster(User user);
	
	void saveRoaster(User user,Calendar calendar);
	
	List<Roaster> getMonthlyRoaster(RoasterDTO roasterDTO);
	
	void updateRoaster(RoasterDTO roaster);
	
	void createRoasters();
}
