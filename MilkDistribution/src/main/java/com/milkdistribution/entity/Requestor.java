package com.milkdistribution.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "REQUESTOR", catalog = "MilkDonor", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class Requestor {
	
	
	private String id;

	private String babyBornDate;

	private String ageInWeeks;
	
	private String babyBornAt;
	
	private String medicalCondition;
	
	private String isBabyInICU;
	
	private String hospital;
	
	private String city;
	
	private String mobileNumber;
	
	private String address;
	
	private String deviceId;
	
	private Authentication authentication;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BABY_BORN_DATE", unique = false, nullable = true, length = 20)
	public String getBabyBornDate() {
		return babyBornDate;
	}

	public void setBabyBornDate(String babyBornDate) {
		this.babyBornDate = babyBornDate;
	}
	
	@Column(name = "AGE", unique = false, nullable = true, length = 20)
	public String getAgeInWeeks() {
		return ageInWeeks;
	}

	public void setAgeInWeeks(String ageInWeeks) {
		this.ageInWeeks = ageInWeeks;
	}

	@Column(name = "BABY_BORN_AT", unique = false, nullable = true, length = 20)
	public String getBabyBornAt() {
		return babyBornAt;
	}

	public void setBabyBornAt(String babyBornAt) {
		this.babyBornAt = babyBornAt;
	}
	
	@Column(name = "MEDICAL_CONDITION", unique = false, nullable = true, length = 20)
	public String getMedicalCondition() {
		return medicalCondition;
	}

	public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}
	
	@Column(name = "IS_BABY_IN_ICU", unique = false, nullable = true, length = 3)
	public String getIsBabyInICU() {
		return isBabyInICU;
	}

	public void setIsBabyInICU(String isBabyInICU) {
		this.isBabyInICU = isBabyInICU;
	}
	
	@Column(name = "HOSPITAL", unique = false, nullable = true, length = 100)
	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	
	@Column(name = "CITY", unique = false, nullable = true, length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "MOBILE", unique = false, nullable = true, length = 15)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	@Column(name = "ADDRESS", unique = false, nullable = true, length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "DEVICE_ID", unique = false, nullable = true, length = 20)
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "AUTENTICATION_ID")
	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}	
	
}
