package com.jfdh.httpmodel;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfdh.util.CustomDateSerializer;
import com.jfdh.util.CustomStatusSerializer;

public class HttpScoreExchangeRecord {
	private String id;
	//登记人员
	private HttpUserOnly operator;
	private HttpScoreShopItem scoreShop;
	
	private int number;
	//兑换码
	private String coupon;
	//登记日期
	@JsonSerialize(using = CustomDateSerializer.class)  
	private Date createDate;
	//使用状态
	@JsonSerialize(using = CustomStatusSerializer.class)  
	private boolean status;
	//使用时间
	@JsonSerialize(using = CustomDateSerializer.class)  
	private Date verifyDate;
	//兑换有效期
	private Date expiry;
	//使用者
	private HttpUserOnly exchanger;
	//获取积分
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HttpUserOnly getOperator() {
		return operator;
	}
	public void setOperator(HttpUserOnly operator) {
		this.operator = operator;
	}
	public HttpScoreShopItem getScoreShop() {
		return scoreShop;
	}
	public void setScoreShop(HttpScoreShopItem scoreShop) {
		this.scoreShop = scoreShop;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	public Date getExpiry() {
		return expiry;
	}
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	public HttpUserOnly getExchanger() {
		return exchanger;
	}
	public void setExchanger(HttpUserOnly exchanger) {
		this.exchanger = exchanger;
	}

	
}
