package com.jfdh.controller.weichat.message;

import java.util.Map;

public class Message {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Map<String,Field> data;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public Map<String, Field> getData() {
		return data;
	}
	public void setData(Map<String, Field> data) {
		this.data = data;
	}
	
	
}
