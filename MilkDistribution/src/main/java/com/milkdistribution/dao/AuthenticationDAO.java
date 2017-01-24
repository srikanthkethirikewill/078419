package com.milkdistribution.dao;

import com.milkdistribution.entity.Authentication;

public interface AuthenticationDAO {

	void save(Authentication authentication);
	
	void update(Authentication authentication);
	
	Authentication getAuthentication(String mobileNo);
}
