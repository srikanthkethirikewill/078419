package com.milkdistribution.dto;

import java.util.Set;

import com.milkdistribution.entity.RoasterDetail;
import com.milkdistribution.entity.User;

public class RoasterDTO {
	
	private String month;
	
	private String year;
	
	private User user;
	
	private String fromDate;
	
	private String toDate;
	
	private Set<RoasterDetail> roasterDetails;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Set<RoasterDetail> getRoasterDetails() {
		return roasterDetails;
	}

	public void setRoasterDetails(Set<RoasterDetail> roasterDetails) {
		this.roasterDetails = roasterDetails;
	}

}
