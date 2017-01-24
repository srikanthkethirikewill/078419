package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Requestor;

public interface RequestorServiceDAO {
	void save(Requestor requestor);
	
	List<Requestor> list();
}
