package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Product;

public class ProductDAOImpl extends CustomHibernateDaoSupport implements ProductDAO {

	@Override
	public void save(Product product) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(product);

	}
	
	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(product);

	}

	@Override
	public Product getProduct(String id) {
		// TODO Auto-generated method stub
		List<?> list = getHibernateTemplate().findByNamedQuery("findProduct", new Object[]{id});
		if (list == null || list.size()==0) {
			return null;
		}
		return (Product)list.get(0);
	}

	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(Product.class);
	}

}
