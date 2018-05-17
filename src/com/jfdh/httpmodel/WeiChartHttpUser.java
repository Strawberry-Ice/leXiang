package com.jfdh.httpmodel;

/**
 * 用户模型
 * 
 * @author
 * 
 */
public class WeiChartHttpUser {
	private String id;
	private String logo;
	private String tel;
	private String nickName;
	private String userName;
	private String address;
	
	private String headimgurl;
	
	private String govOrgId;
	private String openid;
	//是否是匿名用户
	private String mode;
	//是否接受系统推送的消息
	private boolean needReceive;
	
	public boolean getNeedReceive() {
		return needReceive;
	}
	public void setNeedReceive(boolean needReceive) {
		this.needReceive = needReceive;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getGovOrgId() {
		return govOrgId;
	}
	public void setGovOrgId(String govOrgId) {
		this.govOrgId = govOrgId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
