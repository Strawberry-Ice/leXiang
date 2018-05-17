package com.jfdh.httpmodel;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserProfile {
	@NotEmpty(message="昵称不能为空！！")
	@Length(max=100,message="昵称不能超过50个字符！！")
	private String nickName;
	@NotEmpty(message="电话不能为空！！")
	@Length(max=100,message="电话不能超过50个字符！！")
	private String tel;
	@NotEmpty(message="地址不能为空！！")
	@Length(max=100,message="地址不能超过50个字符！！")
	private String address;
	private String logo;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
