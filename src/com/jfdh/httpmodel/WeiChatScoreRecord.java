package com.jfdh.httpmodel;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfdh.util.CustomDateShortSerializer;

public class WeiChatScoreRecord {
	private String id;
	private String message;
	@JsonSerialize(using = CustomDateShortSerializer.class)  
	private Date createDate;
	private Integer score;
	private boolean isPlus;
	private Integer number;
	private String logo;
	private String name;
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public boolean isPlus() {
		return isPlus;
	}
	public void setPlus(boolean isPlus) {
		this.isPlus = isPlus;
	}
	
}
