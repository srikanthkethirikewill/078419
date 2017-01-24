package com.milkdistribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.milkdistribution.dao.ProductDAO;
import com.milkdistribution.entity.Product;

@Service("productService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductDAO productDAO;

	@Override
	public List<Product> getList() {
		// TODO Auto-generated method stub
		return productDAO.list();
	}

	@Override
	public void createProduct(Product product) {
		// TODO Auto-generated method stub
		productDAO.save(product);
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		Product productObj = productDAO.getProduct(product.getId());
		productObj.setDescription(product.getDescription());
		productObj.setPrice(product.getPrice());
		productDAO.save(productObj);
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		Product productObj = productDAO.getProduct(product.getId());
		productDAO.delete(productObj);
	}

}
