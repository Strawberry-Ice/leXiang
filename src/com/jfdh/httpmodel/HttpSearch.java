package com.jfdh.httpmodel;

import java.util.List;

public class HttpSearch {
	private String groupOp;
	private List<HttpData> rules;
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<HttpData> getRules() {
		return rules;
	}
	public void setRules(List<HttpData> rules) {
		this.rules = rules;
	}
	
	
}
