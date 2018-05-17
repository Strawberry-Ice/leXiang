package com.jfdh.model;

import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 
 * @author wangfei
 *栏目表，如招募，活动
 */
@Entity
@Table(name="category")
public class Category {
	
	@Id
	@Column(length=36)
	private String id;
	
	//栏目名称
	@Column(length=50)
	private String name;
	
	//栏目名称
	@Column(length=10)
	private String code;
	
	@Column(columnDefinition = "varchar(300)")
	private String logo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	//create by
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User creator;
	
	//是否有效
	private boolean valid;
	
	//如二手中的二级栏目
	@ManyToOne(fetch = FetchType.EAGER)  
    @JoinColumn(name = "parent_id")  
	private Category parent; //columnId
	@OneToMany(targetEntity = Category.class, cascade = { CascadeType.DETACH }, mappedBy = "parent",fetch=FetchType.LAZY)  
	private List<Category> children = new ArrayList<Category>();

	@OneToMany(mappedBy = "category", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Activity> activities = new ArrayList<Activity>();

	private BigDecimal seq;
	
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}
	
}
