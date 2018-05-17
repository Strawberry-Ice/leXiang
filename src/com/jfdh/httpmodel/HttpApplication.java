package com.jfdh.httpmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 报名数据
 * @author wangfei
 *
 */
public class HttpApplication {
	private String id;
	
	private String activityName;
	
	private String applyName;
	
	private String applyTel;
	
	private String applyDate;

	private String status;
	
	private String seq;
	
	private String activityId;
	
	private String userLogo;
	
	private List<HttpFieldValue> values=new ArrayList<HttpFieldValue>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyTel() {
		return applyTel;
	}

	public void setApplyTel(String applyTel) {
		this.applyTel = applyTel;
	}

	public List<HttpFieldValue> getValues() {
		return values;
	}

	public void setValues(List<HttpFieldValue> values) {
		this.values = values;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}
	
}
