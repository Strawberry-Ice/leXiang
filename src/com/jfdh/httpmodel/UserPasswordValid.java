package com.jfdh.httpmodel;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserPasswordValid {
	@NotEmpty(message="旧密码不能为空！！")
	@Length(max=100,message="密码不能超过50个字符！！")
	private String oldPassword;
	@NotEmpty(message="新密码不能为空！！")
	@Length(max=100,message="密码不能超过50个字符！！")
	private String password;
	@NotEmpty(message="密码确认不能为空！！")
	@Length(max=100,message="密码不能超过50个字符！！")
	private String password2;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
