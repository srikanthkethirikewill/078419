package com.milkdistribution.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Requestor;

@Repository("requestorDao")
public class RequestorServiceDAOImpl extends CustomHibernateDaoSupport implements RequestorServiceDAO{

	@Override
	public void save(Requestor requestor) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(requestor);
	}

	@Override
	public List<Requestor> list() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(Requestor.class);		
	}

}
