package com.jfdh.httpmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * 用户模型
 * 
 * @author
 * 
 */
public class HttpUser{

		private String userName;
		private String password;
		private int integral;
		private String nickName;
		
		private String tel;
		
		private String address;
		
		private String introduction;
		
		private String imageUrl;
		
		private String kaptcha;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getIntegral() {
			return integral;
		}

		public void setIntegral(int integral) {
			this.integral = integral;
		}

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

		public String getIntroduction() {
			return introduction;
		}

		public void setIntroduction(String introduction) {
			this.introduction = introduction;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getKaptcha() {
			return kaptcha;
		}

		public void setKaptcha(String kaptcha) {
			this.kaptcha = kaptcha;
		}
		
}
