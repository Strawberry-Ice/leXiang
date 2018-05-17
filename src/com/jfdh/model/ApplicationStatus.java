package com.jfdh.model;

public enum ApplicationStatus {
	FINISHED("完成活动"),
	UNFINISHED("未完成活动");
	
	private ApplicationStatus(String name) {
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
