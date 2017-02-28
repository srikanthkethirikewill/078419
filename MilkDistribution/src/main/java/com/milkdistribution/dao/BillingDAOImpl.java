package com.milkdistribution.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.User;

@Repository("billingDao")
public class BillingDAOImpl extends CustomHibernateDaoSupport implements BillingDAO {

	@Override
	public void save(Billing billing) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(billing);
	}
	
	@Override
	public Billing getBilling(String id) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findBill", new Object[]{id});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Billing)list.get(0);
	}
	
	@Override
	public Billing getBilling(String month,String year,User user) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findCurrentMonthBill", new Object[]{month,year,user});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Billing)list.get(0);
	}

	@Override
	public List<Billing> getBilling(User user) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findBillingList", new Object[]{user});
		List<Billing> billingList = new ArrayList<Billing>();
		for(Object obj:list) {
			Billing billing = (Billing)obj;
			billingList.add(billing);
		}
		return billingList;
	}

	@Override
	public Billing getBillingUser(String userId, String month, String year) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findBilling", new Object[]{userId,month,year});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Billing)list.get(0);
	}

}
