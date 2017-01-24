package com.milkdistribution.service;

import java.util.List;

import com.milkdistribution.entity.Product;

public interface ProductService {

	List<Product> getList();
	
	void createProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProduct(Product product);
}
