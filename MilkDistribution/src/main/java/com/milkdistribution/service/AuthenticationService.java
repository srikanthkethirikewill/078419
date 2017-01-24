package com.milkdistribution.service;

import com.milkdistribution.entity.Authentication;

public interface AuthenticationService {
	
	void save(Authentication authentication);
	
	void update(Authentication authentication);
	
	Authentication getAuthentication(String mobileNo);
}
