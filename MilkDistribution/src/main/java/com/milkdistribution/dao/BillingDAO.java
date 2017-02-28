package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.User;

public interface BillingDAO {
	
	void save(Billing billing);
	
	List<Billing> getBilling(User user);
	
	Billing getBillingUser(String userId, String month,String year);
	
	Billing getBilling(String id);
	
	Billing getBilling(String month,String year,User user);
}
