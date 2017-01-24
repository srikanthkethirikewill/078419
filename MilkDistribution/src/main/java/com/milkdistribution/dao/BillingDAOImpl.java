package com.milkdistribution.dao;

import java.util.ArrayList;
import java.util.List;

import com.milkdistribution.entity.Billing;

public class BillingDAOImpl extends CustomHibernateDaoSupport implements BillingDAO {

	@Override
	public void save(Billing billing) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(billing);
	}
	
	public Billing getBilling(String id) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findBill", new Object[]{id});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Billing)list.get(0);
	}

	@Override
	public List<Billing> getBilling(String month, String year) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findBillingList", new Object[]{month,year});
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
