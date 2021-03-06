package com.milkdistribution.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Area;
import com.milkdistribution.entity.User;

@Repository("userDao")
public class UserDAOImpl  extends CustomHibernateDaoSupport implements UserDAO {

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		System.out.println("before " + user.getId());
		getHibernateTemplate().saveOrUpdate(user);
		System.out.println("success");
	}
	
	@Override
	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}
	
	@Override
	public User getUserByUserId(String userId) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findUser", new Object[]{userId});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (User)list.get(0);
	}
	
	@Override
	public User getUserByMobile(String mobile) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findUserByMobile", new Object[]{mobile});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (User)list.get(0);
	}
	
	@Override
	public User getUserByMailId(String mailId) {
		List<?> list = getHibernateTemplate().findByNamedQuery("findUserByMail", new Object[]{mailId});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (User)list.get(0);
	}
	
	@Override
	public List<User> getActiveUsers() {
		List<?> list = getHibernateTemplate().findByNamedQuery("getUsers", new Object[]{"A"});
		List<User> userList = new ArrayList<User>();
		for(Object obj:list) {
			User roaster=(User)obj;
			userList.add(roaster);
		}
		return userList;
	}
	
	@Override
	public List<User> getActiveUsersByArea(Area area) {
		List<?> list = getHibernateTemplate().findByNamedQuery("getUsersByArea", new Object[]{"A", area});
		List<User> userList = new ArrayList<User>();
		for(Object obj:list) {
			User roaster=(User)obj;
			userList.add(roaster);
		}
		return userList;
	}
	
	@Override
	public List<User> getPendingUsers() {
		List<?> list = getHibernateTemplate().findByNamedQuery("getUsers", new Object[]{"I"});
		List<User> userList = new ArrayList<User>();
		for(Object obj:list) {
			User roaster=(User)obj;
			userList.add(roaster);
		}
		return userList;
	}

	@Override
	public List<User> getUsers(boolean onlyAdmin) {
		// TODO Auto-generated method stub
		String query = "from User  r " + (onlyAdmin ? "where r.role = ?" : "") + " group by r.area";
		Object[] values = onlyAdmin ? new Object[] {"A"} : new Object[] {};
		List<?> list = getHibernateTemplate().find(query, values);
		List<User> userList = new ArrayList<User>();
		for(Object obj:list) {
			User roaster=(User)obj;
			userList.add(roaster);
		}
		return userList;
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findUserById", new Object[]{userId});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (User)list.get(0);
	}
	
	@Override
	public User getUserBySequence(int seq) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findUser", new Object[]{seq});
		if (list == null || list.size()==0) {
			return null;
		}
		
		return (User)list.get(0);
	}

}
