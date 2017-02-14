package com.milkdistribution.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
	@NamedQuery(
	name = "monthlyBill",
	query = "from Roaster s where s.status = ? and s.date >= ? and s.date<= ? group by s.user"
	),
	@NamedQuery(
	name = "findRoaster",
	query = "from Roaster s where s.id = ?"
	)
})

@Entity
@Table(name = "ROASTER", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class Roaster {
	
	private String id;
	
	private Date date;
	
	private User user;
	
	@Formula("(SELECT SUM(D.RATE) FROM ROASTER_DTL D WHERE D.ROASTER_ID = ID)")
	private double amount;
	
	private String status;
	
	private Area area;
	
	private Set<RoasterDetail> roasterDetails;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "DATE", unique = false, nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "AREA_ID")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "roaster", orphanRemoval = true)
	public Set<RoasterDetail> getRoasterDetails() {
		return roasterDetails;
	}

	public void setRoasterDetails(Set<RoasterDetail> roasterDetails) {
		this.roasterDetails = roasterDetails;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
