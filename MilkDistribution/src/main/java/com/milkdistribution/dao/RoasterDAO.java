package com.milkdistribution.dao;

import java.util.Date;
import java.util.List;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;

public interface RoasterDAO {
	
	void save(Roaster roaster);
	
	void delete(Date date,User user);
	
	List<Roaster> list(Date date);
	
	List<Billing> prepareBilling();
	
	void updateProductCosts(Product product);
	
	List<Roaster> getMonthlyRoaster(String month,String year,User user);
	
	Roaster geRoasterById(String id);
	
	void deleteByRange(Date fromDate,Date toDate,User user);

}
