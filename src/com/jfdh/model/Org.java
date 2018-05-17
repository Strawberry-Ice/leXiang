package com.jfdh.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="org")
public class Org {
	@Id
	@Column(length=36)
	private String id;
	private boolean profitable = false;
	@Column(length=50)
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "org", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreShopItem> scoreShopItems = new ArrayList<ScoreShopItem>();
	@JsonIgnore
	@OneToMany(mappedBy = "org", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>();
	@JsonIgnore
	@OneToOne(mappedBy = "org", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private ScorePool scorePool;
	
	@JsonIgnore
	@OneToMany(mappedBy = "govOrg", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Activity> activities = new ArrayList<Activity>();

	public List<ScoreShopItem> getScoreShopItems() {
		return scoreShopItems;
	}
	public void setScoreShopItems(List<ScoreShopItem> scoreShopItems) {
		this.scoreShopItems = scoreShopItems;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=100)
	private String tel;

	// 地址
	@Column(length=200)
	private String address;
	
	public ScorePool getScorePool() {
		return scorePool;
	}
	public void setScorePool(ScorePool scorePool) {
		this.scorePool = scorePool;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isProfitable() {
		return profitable;
	}
	public void setProfitable(boolean profitable) {
		this.profitable = profitable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
}
