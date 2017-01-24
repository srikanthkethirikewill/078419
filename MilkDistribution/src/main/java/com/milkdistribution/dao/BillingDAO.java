package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Billing;

public interface BillingDAO {
	
	void save(Billing billing);
	
	List<Billing> getBilling(String month,String year);
	
	Billing getBillingUser(String userId, String month,String year);
	
	Billing getBilling(String id);
}
