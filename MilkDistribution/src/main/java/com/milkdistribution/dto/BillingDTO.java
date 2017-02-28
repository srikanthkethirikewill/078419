package com.milkdistribution.dto;

import java.util.List;

import com.milkdistribution.entity.Billing;
import com.milkdistribution.entity.User;

public class BillingDTO {

	private String month;
	
	private String year;
	
	private User user;
	
	private List<Billing> billList;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Billing> getBillList() {
		return billList;
	}

	public void setBillList(List<Billing> billList) {
		this.billList = billList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
