package com.jfdh.httpmodel;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 
 * @author wangfei
 *栏目表，如招募，活动
 */
public class HttpCategory {
	
	private String id;
	
	private String name;
	
	private String code;
	
	private String logo;
	
	private List<HttpCategory> children = new ArrayList<HttpCategory>();


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


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<HttpCategory> getChildren() {
		return children;
	}


	public void setChildren(List<HttpCategory> children) {
		this.children = children;
	}
	
}
