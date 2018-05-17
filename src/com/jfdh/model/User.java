package com.jfdh.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfdh.util.CustomDateSerializer;
@Entity
@Table(name="user")
public class User {
	// ID
	@Id
	@Column(length=36)
	private String id;
	// 用户名
	@Column(length=100,unique = true)
	private String userName;
	// 密码
	@Column(length=100)
	private String password;
	// 积分
	// private int integral;
	// 昵称
	@Column(length=50)
	@NotEmpty(message="昵称不能为空！！")
	@Length(max=100,message="昵称不能超过50个字符！！")
	private String nickName;

	private Boolean state=true;
	@Temporal(TemporalType.TIMESTAMP)
	private Date bundlingDate;
	
	//是否接受系统推送的消息
	private boolean needReceive = true;
	
	private boolean virtual=false;

	public Date getBundlingDate() {
		return bundlingDate;
	}
	
	@Column(length=200,unique=true)
	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setBundlingDate(Date bundlingDate) {
		this.bundlingDate = bundlingDate;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
	@JsonIgnore
	@Transient
	private String parentGovOrg;
	
	
	public String getParentGovOrg() {
		return parentGovOrg;
	}

	public void setParentGovOrg(String parentGovOrg) {
		this.parentGovOrg = parentGovOrg;
	}
	// 电话
	@Column(length=100)
	private String tel;

	// 地址
	@Column(length=200)
	@NotEmpty(message="地址不能为空！！")
	@Length(max=100,message="地址不能超过50个字符！！")
	private String address;

	// 描述
	@Column(columnDefinition = "varchar(500)")
	@Length(max=100,message="描述不能超过500个字符！！")
	private String description;

	// 头像链接
	@Column(columnDefinition = "varchar(300)")
	private String logo;
	@JsonSerialize(using = CustomDateSerializer.class)  
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "govOrgId", referencedColumnName = "id")
	private GovOrg govOrg;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "orgId", referencedColumnName = "id")
	private Org org;

	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "roleId", referencedColumnName = "id")
	private Role role;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ActivityComments> comments=new ArrayList<ActivityComments>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Application> applications=new ArrayList<Application>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Category> categories=new ArrayList<Category>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Activity> activities=new ArrayList<Activity>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "operator", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreExchangeRecord> operatorScoreExchangeRecords=new ArrayList<ScoreExchangeRecord>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "exchanger", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreExchangeRecord> exchangerScoreExchangeRecords=new ArrayList<ScoreExchangeRecord>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private ScorePool scorePool;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public GovOrg getGovOrg() {
		return govOrg;
	}

	public void setGovOrg(GovOrg govOrg) {
		this.govOrg = govOrg;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}

	public List<ActivityComments> getComments() {
		return comments;
	}

	public void setComments(List<ActivityComments> comments) {
		this.comments = comments;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<ScoreExchangeRecord> getOperatorScoreExchangeRecords() {
		return operatorScoreExchangeRecords;
	}

	public void setOperatorScoreExchangeRecords(
			List<ScoreExchangeRecord> operatorScoreExchangeRecords) {
		this.operatorScoreExchangeRecords = operatorScoreExchangeRecords;
	}

	public List<ScoreExchangeRecord> getExchangerScoreExchangeRecords() {
		return exchangerScoreExchangeRecords;
	}

	public void setExchangerScoreExchangeRecords(
			List<ScoreExchangeRecord> exchangerScoreExchangeRecords) {
		this.exchangerScoreExchangeRecords = exchangerScoreExchangeRecords;
	}

	public ScorePool getScorePool() {
		return scorePool;
	}

	public void setScorePool(ScorePool scorePool) {
		this.scorePool = scorePool;
	}

	public boolean getNeedReceive() {
		return needReceive;
	}

	public void setNeedReceive(boolean needReceive) {
		this.needReceive = needReceive;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	
}




