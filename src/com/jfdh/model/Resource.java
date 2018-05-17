package com.jfdh.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="resource")
public class Resource{
	@Id
	@Column(length=36)
	private String id;
	@Column(length=50)
	private String name;
	@Column(length=50,unique=true)
	private String resKey;//这个权限KEY是唯一的，新增时要注意，
	@Column(length=200)
	private String resUrl;//URL地址．例如：/videoType/query　　不需要项目名和http://xxx:8080
	private Integer level;//级别 菜单的顺序
	@Column(length=5)
	private String type;//类型，目录　菜单  按扭．．在spring security3安全权限中，涉及精确到按扭控制
	@Column(columnDefinition = "varchar(500)")
	private String description;
	@Column(columnDefinition = "varchar(200)")
	private String logo;
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.DETACH)  
    @JoinColumn(name = "parent_id")  
	private Resource parent;
	@OneToMany(targetEntity = Resource.class, cascade = { CascadeType.DETACH }, mappedBy = "parent",fetch=FetchType.LAZY)  
	@OrderBy("level ASC")
	private List<Resource> children = new ArrayList<Resource>();

	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "resource_role", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles=new HashSet<Role>();

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
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

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
}