package com.jfdh.model;

public enum VerifyStatus {
	UNVERIFIED("未审核"),
	PASS("通过"),
	FAIL("报名未通过"),
	SELLING("出售中"),
	SOLD("已出售"),
	CANCLE("取消报名");
	private VerifyStatus(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}

	String name;
}
