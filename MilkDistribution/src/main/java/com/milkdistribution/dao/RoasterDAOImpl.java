package com.milkdistribution.dao;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;

public class RoasterDAOImpl extends CustomHibernateDaoSupport implements RoasterDAO{

	@Override
	public void save(Roaster roaster) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(roaster);
	}
	
	@Override
	public void delete(Date date,User user) {
		getHibernateTemplate().bulkUpdate("delete from Roaster where user = ? and date >= ?", new Object[] {user,date});
	}
	
	public void updateProductCosts(Product product) {
		getHibernateTemplate().bulkUpdate("update RoasterDetail set rate = ? where product = ?", new Object[] {product.getPrice(),product});
	}

	@Override
	public List<Roaster> list(Date date) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().find("from Roaster  r where r.date = ? and r.status = ? group by r.area", new Object[] {date, "A"});
		List<Roaster> roasterList = new ArrayList<Roaster>();
		for(Object obj:list) {
			Roaster roaster=(Roaster)obj;
			roasterList.add(roaster);
		}
		return roasterList;
		/*DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Roaster.class);
		detachedCriteria.add(Restrictions.eq("", ""));
		detachedCriteria.add(Restrictions.eq("", ""));
		return null;*/
		//return (List<Roaster>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	@Override
	public List<Billing> prepareBilling() {
		// TODO Auto-generated method stub
		String query = "select s.user,sum(s.billAmount) from Roaster s where s.status = ? and s.date >= ? and s.date<= ? group by s.user";
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
		fromCalendar.set(Calendar.HOUR, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.set(Calendar.HOUR, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		DateFormatSymbols symbols = new DateFormatSymbols();
		String month = symbols.getMonths()[fromCalendar.get(Calendar.MONTH)];
		Object[] values = new Object[] {"A", fromCalendar.getTime(), toCalendar.getTime()};
		List<?> list = getHibernateTemplate().find(query, values);
		List<Billing> billingList = new ArrayList<Billing>();
		for(Object obj:list) {
			Object[] row = (Object[])obj;
			Billing billing = new Billing();
			billing.setStatus("I");
			billing.setMonth(month);
			billing.setYear(fromCalendar.get(Calendar.YEAR)+"");
			billing.setUser((User)row[0]);
			billing.setBillAmount((Double)row[1]);
			billingList.add(billing);
		}
		return billingList;
	}
	
	
}
