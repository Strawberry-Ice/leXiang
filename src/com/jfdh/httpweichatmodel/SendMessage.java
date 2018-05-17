package com.jfdh.httpweichatmodel;

public class SendMessage {
	private String code;
	private String activityId;
	private String openId;
	private String access_token;
	private String first;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String remark;
	
	private String FieldName;
	private String Account;
	private String change;
	private String CreditChange;
	private String CreditTotal;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getCreditChange() {
		return CreditChange;
	}
	public void setCreditChange(String creditChange) {
		CreditChange = creditChange;
	}
	public String getCreditTotal() {
		return CreditTotal;
	}
	public void setCreditTotal(String creditTotal) {
		CreditTotal = creditTotal;
	}
	
}
