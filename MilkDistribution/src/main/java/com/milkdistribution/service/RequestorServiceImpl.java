package com.milkdistribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.RequestorServiceDAO;
import com.milkdistribution.entity.Requestor;

@Service("requestorService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class RequestorServiceImpl implements RequestorService{
	
	@Autowired
	RequestorServiceDAO serviceDAO;
	@Override
	public void save(Requestor requestor) {
		// TODO Auto-generated method stub
		serviceDAO.save(requestor);
	}

	@Override
	public List<Requestor> list() {
		// TODO Auto-generated method stub
		return serviceDAO.list();
	}

}
