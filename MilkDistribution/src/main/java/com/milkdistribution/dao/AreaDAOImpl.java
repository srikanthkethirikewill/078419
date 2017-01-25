package com.milkdistribution.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.milkdistribution.entity.Area;

@Repository("areaDao")
public class AreaDAOImpl extends CustomHibernateDaoSupport implements AreaDAO {

	@Override
	public void save(Area area) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(area);
	}
	
	@Override
	public void delete(Area area) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(area);
	}

	@Override
	public Area getArea(String id) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findArea", new Object[]{id});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Area)list.get(0);
	}

	@Override
	public List<Area> list() {
		// TODO Auto-generated method stub
		List<Area> list = getHibernateTemplate().loadAll(Area.class);
		return list;
	}

}
