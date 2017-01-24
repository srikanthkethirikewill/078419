package com.milkdistribution.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
	@NamedQuery(
	name = "findUser",
	query = "from User s where s.userId = ?"
	),
	@NamedQuery(
	name = "findUserById",
	query = "from User s where s.id = ?"
	),
	@NamedQuery(
	name = "findUserBySeq",
	query = "from User s where s.seq = ?"
	),
	@NamedQuery(
	name = "findUserByMail",
	query = "from User s where s.mailId = ?"
	),
	@NamedQuery(
	name = "getUsers",
	query = "from User s where s.status = ?"
	)
})
@Entity
@Table(name = "USER", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class User {
	
	private String id;
	
	private String userId;
	
	private String password;
	
	private String mailId;
	
	private String mobile;
	
	private String address;
	
	private String status;
	
	private String role;
	
	private Area area;
	
	private Set<UserDailyRequirement> requirement;
	
	private int seq;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "USER_ID", unique = true, nullable = false, length = 50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "PASSWORD", unique = false, nullable = false, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "MAIL", unique = false, nullable = true, length = 50)
	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	@Column(name = "MOBILE", unique = true, nullable = false, length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "ADDRESS", unique = false, nullable = true, length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "STATUS", unique = false, nullable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "ROLE", unique = false, nullable = true, length = 10)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "AREA")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "USER_DAILY_REQUIREMENT", orphanRemoval = true)
	public Set<UserDailyRequirement> getRequirement() {
		return requirement;
	}
	
	public void setRequirment(Set<UserDailyRequirement> requirement) {
		this.requirement = requirement;
	}

	@Column(name = "SEQ", unique = false, nullable = true, length = 10)
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}
