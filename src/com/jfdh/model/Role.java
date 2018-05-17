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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="role")
public class Role {
	@Id
	@Column(length=36)
	private String id;
	private boolean enable;// 是否禁用角色　1　表示禁用　2　表示不禁用
	@Column(length=50)
	private String name;
	@Column(length=50,unique=true)
	private String roleKey;// 唯一,新境时,需要判断
	@Column(columnDefinition = "varchar(500)")
	private String description;
	@JsonIgnore
	@ManyToMany(mappedBy="roles",cascade=CascadeType.DETACH)
	@OrderBy("level ASC")
	private List<Resource> resources = new ArrayList<Resource>();

	@JsonIgnore
	@OneToMany(mappedBy = "role", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<User> users=new HashSet<User>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}