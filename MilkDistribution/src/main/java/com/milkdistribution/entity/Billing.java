package com.milkdistribution.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
	@NamedQuery(
	name = "findBilling",
	query = "from Billing s where s.user.userId = ? and s.month = ? and s.year = ?"
	),
	@NamedQuery(
	name = "findBillingList",
	query = "from Billing s where s.month = ? and s.year = ?"
	),
	@NamedQuery(
	name = "findBill",
	query = "from Billing s where s.id = ?"
	)
})
@Entity
@Table(name = "BILLING", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class Billing {
	
	private String id;
	
	private String month;
	
	private String Year;
	
	private double billAmount;
	
	private User user;
	
	private double receivedAmount;
	
	private String status;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MONTH", unique = false, nullable = false, length=3)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	@Column(name = "YEAR", unique = false, nullable = false, length=4)
	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}
	
	@Column(name = "BILL_AMOUNT", unique = false, nullable = true)
	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "RECEIVED_AMOUNT", unique = false, nullable = true)
	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, length=1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
