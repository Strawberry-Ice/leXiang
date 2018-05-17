package com.jfdh.httpmodel;


/**
 * 评论
 * @author Administrator
 *
 */
public class HttpActivityComments{
	private String id;
	//品论内容
	private String content;
	//
	private String activityId;
	
	private String createDate;
	
	private HttpUser httpUser;
	
	private String openId;
	
	private String nickName;
	
	private String address;
	
	private String headimgurl;
	
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public HttpUser getHttpUser() {
		return httpUser;
	}
	public void setHttpUser(HttpUser httpUser) {
		this.httpUser = httpUser;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
