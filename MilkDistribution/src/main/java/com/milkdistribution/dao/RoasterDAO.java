package com.milkdistribution.dao;

import java.util.Date;
import java.util.List;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;

public interface RoasterDAO {
	
	void save(Roaster roaster);
	
	void delete(Date date,User user);
	
	List<Roaster> list(Date date);
	
	List<Billing> prepareBilling();

}
