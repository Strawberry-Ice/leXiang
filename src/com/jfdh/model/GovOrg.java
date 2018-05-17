package com.jfdh.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="govorg")
public class GovOrg {

	@Id
	@Column(length = 36)
	private String id;
	// 角色名称
	@Column(length = 50)
	private String name;
	// 角色代码
	@Column(length = 50)
	@JsonIgnore
	private String code;
	// 排序
	@JsonIgnore
	private BigDecimal sequence;
	// 是否有效
	@JsonIgnore
	private boolean valid;
	// 备注
	@JsonIgnore
	@Column(columnDefinition = "varchar(500)")
	private String remark;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "parent_id")
	private GovOrg parent; // columnId
	@JsonIgnore
	private Boolean adminOrg;
	private Integer level;
	
	@JsonIgnore
	@OneToMany(targetEntity = GovOrg.class, cascade = { CascadeType.PERSIST }, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<GovOrg> children = new ArrayList<GovOrg>();
	@JsonIgnore
	@OneToMany(mappedBy = "govOrg", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Activity> activities = new ArrayList<Activity>();
	@JsonIgnore
	@OneToMany(mappedBy = "govOrg", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	@JsonIgnore
	@OneToOne(mappedBy = "govOrg", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private ScorePool scorePool;
	@JsonIgnore
	@OneToMany(mappedBy = "govOrg", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>();

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	// 电话
	@Column(length = 100)
	private String tel;

	// 地址
	@Column(length = 200)
	private String address;

	public Boolean getAdminOrg() {
		return adminOrg;
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

	public void setAdminOrg(Boolean adminOrg) {
		this.adminOrg = adminOrg;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getSequence() {
		return sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GovOrg getParent() {
		return parent;
	}

	public void setParent(GovOrg parent) {
		this.parent = parent;
	}

	public List<GovOrg> getChildren() {
		return children;
	}

	public void setChildren(List<GovOrg> children) {
		this.children = children;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
