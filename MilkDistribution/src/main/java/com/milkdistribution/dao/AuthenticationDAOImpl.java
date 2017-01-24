package com.milkdistribution.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Authentication;

@Repository("authenticationDao")
public class AuthenticationDAOImpl  extends CustomHibernateDaoSupport implements AuthenticationDAO{

	@Override
	public void save(Authentication authentication) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(authentication);
		
	}
	
	@Override
	public void update(Authentication authentication) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(authentication);
		
	}

	@Override
	public Authentication getAuthentication(String mobileNo) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findAuthentication", new Object[]{mobileNo});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Authentication)list.get(0);
	}

}
