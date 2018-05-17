package com.jfdh.controller.weichat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")  
public class Article {
	  
    @XStreamAlias("Title")  
    @XStreamCDATA  
	private String title;
    @XStreamAlias("Description")  
    @XStreamCDATA  
	private String description;
    @XStreamAlias("PicUrl")  
    @XStreamCDATA  
	private String picUrl;
    @XStreamAlias("Url")  
    @XStreamCDATA  
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
