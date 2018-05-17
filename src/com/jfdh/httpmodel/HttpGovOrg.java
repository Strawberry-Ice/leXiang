package com.jfdh.httpmodel;

import java.util.ArrayList;
import java.util.List;
public class HttpGovOrg{

	private String id;

	private String name;

	private List<HttpGovOrg> children = new ArrayList<HttpGovOrg>();

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

	public List<HttpGovOrg> getChildren() {
		return children;
	}

	public void setChildren(List<HttpGovOrg> children) {
		this.children = children;
	}
	
	
	
}
