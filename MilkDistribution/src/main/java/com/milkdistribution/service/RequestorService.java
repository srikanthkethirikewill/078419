package com.milkdistribution.service;

import java.util.List;

import com.milkdistribution.entity.Requestor;

public interface RequestorService {
	
	void save(Requestor requestor);
	
	List<Requestor> list();

}
