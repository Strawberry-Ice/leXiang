package com.jfdh.httpmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ScoreTransfer {
	private String[] orgIds;
	@NotNull(message="划拨积分不能为空！！")  
	@Min(value=1,message="划拨积分不能小于1！！")
	private Integer score;
	
	public String[] getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
