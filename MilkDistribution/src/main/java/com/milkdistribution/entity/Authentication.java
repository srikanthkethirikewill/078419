package com.milkdistribution.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
	@NamedQuery(
	name = "findAuthentication",
	query = "from Authentication s where s.mobileNumber = ?"
	)
})
@Entity
@Table(name = "AUTHENTICATION", catalog = "MilkDonor", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "MOBILE_NO") })
public class Authentication {
	
	
	private String id;	
	
	private String mobileNumber;
	
	private String otp;
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MOBILE_NO", unique = true, nullable = false, length = 15)
	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "OTP", unique = false, nullable = false, length = 4)
	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
}
