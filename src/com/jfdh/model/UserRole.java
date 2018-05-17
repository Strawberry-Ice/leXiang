package com.jfdh.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * SyuserSyrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="userrole")
public class UserRole{

	// Fields
	@Id
	@Column(length=36)
	private String id;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "govOrgId", referencedColumnName = "id")
	private GovOrg govOrg;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GovOrg getGovOrg() {
		return govOrg;
	}
	public void setGovOrg(GovOrg govOrg) {
		this.govOrg = govOrg;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}