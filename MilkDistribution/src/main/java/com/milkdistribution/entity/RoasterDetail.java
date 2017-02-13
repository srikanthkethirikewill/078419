package com.milkdistribution.entity;

import javax.persistence.CascadeType;
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
@Table(name = "ROASTER_DTL", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class RoasterDetail {
	
	private String id;
	
	private Product product;
	
	private int qty;
	
	private double rate;
	
	@JsonIgnore
	private Roaster roaster;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
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

	@Column(name = "RATE", unique = false, nullable = true)
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name= "ROASTER_ID")
	public Roaster getRoaster() {
		return roaster;
	}

	public void setRoaster(Roaster roaster) {
		this.roaster = roaster;
	}
	
	
}
