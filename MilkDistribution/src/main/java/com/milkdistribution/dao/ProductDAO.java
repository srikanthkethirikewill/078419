package com.milkdistribution.dao;

import java.util.List;

import com.milkdistribution.entity.Product;

public interface ProductDAO {
	
	void save(Product product);
	
	void delete(Product product);
	
	Product getProduct(String id);
	
	List<Product> list();
}
