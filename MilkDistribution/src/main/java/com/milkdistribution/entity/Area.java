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
	name = "findArea",
	query = "from Area s where s.id = ?"
	)
})
@Entity
@Table(name = "AREA", catalog = "MilkDistribution", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID") })
public class Area {
	
	private String id;
	
	private String description;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "DESCRIPTION", unique = true, nullable = true, length = 50)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}	
