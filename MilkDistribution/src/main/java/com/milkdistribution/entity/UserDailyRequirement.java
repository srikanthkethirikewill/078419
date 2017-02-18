package com.milkdistribution.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_DAILY_REQUIREMENT", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class UserDailyRequirement {
	
	private String id;
	
	private Product product;
	
	private int qty;
	
	@JsonIgnore
	private User user;
	
	

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "QTY", unique = false, nullable = true)
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name= "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
