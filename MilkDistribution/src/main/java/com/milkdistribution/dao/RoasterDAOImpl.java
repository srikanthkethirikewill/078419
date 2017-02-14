package com.milkdistribution.dao;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.Product;
import com.milkdistribution.entity.Roaster;
import com.milkdistribution.entity.User;

@Repository("roasterDao")
public class RoasterDAOImpl extends CustomHibernateDaoSupport implements RoasterDAO{

	@Override
	public void save(Roaster roaster) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(roaster);
	}
	
	@Override
	public void delete(Date date,User user) {
		java.sql.Date dateObj = new java.sql.Date(date.getTime());
		getHibernateTemplate().bulkUpdate("delete from RoasterDetail d where d.roaster in (from Roaster where user = ? and date >= ?)", new Object[] {user,dateObj});
		getHibernateTemplate().bulkUpdate("delete from Roaster where user = ? and date >= ?", new Object[] {user,dateObj});
	}
	
	@Override
	public void deleteByRange(Date fromDate,Date toDate,User user) {
		java.sql.Date fromDateObj = new java.sql.Date(fromDate.getTime());
		java.sql.Date toDateObj = new java.sql.Date(toDate.getTime());
		getHibernateTemplate().bulkUpdate("delete from RoasterDetail d where d.roaster in (from Roaster where user = ? and date >= ? and date <= ?)", new Object[] {user,fromDateObj, toDateObj});
		getHibernateTemplate().bulkUpdate("delete from Roaster where user = ? and date >= ? and date <= ?", new Object[] {user,fromDateObj, toDateObj});
	}
	
	@Override
	public Roaster geRoasterById(String id) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findRoaster", new Object[]{id});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (Roaster)list.get(0);
	}
	
	@Override
	public void updateProductCosts(Product product) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		java.sql.Date dateObj = new java.sql.Date(calendar.getTime().getTime());
		getHibernateTemplate().bulkUpdate("update RoasterDetail s set s.rate = ? where s.roaster.product = ? and s.roaster.date >= ?", new Object[] {product.getPrice(),product, dateObj});
		
	}

	@Override
	public List<Roaster> list(Date date) {
		// TODO Auto-generated method stub
		java.sql.Date dateObj = new java.sql.Date(date.getTime());
		List<?> list = getHibernateTemplate().find("from Roaster  r where r.date = ? and r.status = ? order by r.area", new Object[] {dateObj, "A"});
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
	public List<Roaster> getMonthlyRoaster(String month,String year,User user) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
		fromCalendar.set(Calendar.MONTH, Integer.parseInt(month));
		fromCalendar.set(Calendar.YEAR, Integer.parseInt(year));
		fromCalendar.set(Calendar.HOUR, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.AM_PM, Calendar.PM);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar = (Calendar)fromCalendar.clone();
		toCalendar.set(Calendar.MONTH, toCalendar.get(Calendar.MONTH)+1);
		// TODO Auto-generated method stub
		java.sql.Date fromDateObj = new java.sql.Date(fromCalendar.getTime().getTime());
		java.sql.Date toDateObj = new java.sql.Date(toCalendar.getTime().getTime());
		List<?> list = getHibernateTemplate().find("from Roaster  r where r.date >= ? and r.date < ? and r.user = ?", new Object[] {fromDateObj, toDateObj,user});
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
		String query = "select s.user,(select sum(d.rate) from RoasterDetail d where d.roaster = s group by d.roaster) from Roaster s where s.status = ? and s.date >= ? and s.date<= ? group by s.user";
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
		fromCalendar.set(Calendar.HOUR, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		fromCalendar.set(Calendar.AM_PM, Calendar.PM);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.set(Calendar.HOUR, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		toCalendar.set(Calendar.AM_PM, Calendar.PM);
		DateFormatSymbols symbols = new DateFormatSymbols();
		String month = symbols.getMonths()[fromCalendar.get(Calendar.MONTH)];
		java.sql.Date fromDateObj = new java.sql.Date(fromCalendar.getTime().getTime());
		java.sql.Date toDateObj = new java.sql.Date(toCalendar.getTime().getTime());
		Object[] values = new Object[] {"A", fromDateObj, toDateObj};
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
