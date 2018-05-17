package com.jfdh.service.weichat;

import org.springframework.data.domain.Page;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.WeiChartHttpUser;
import com.jfdh.model.Activity;
import com.jfdh.model.Application;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.User;

public interface WeichatUserService {
	public User findById(String id);

	public void updateUserInfo(WeiChartHttpUser httpUser,String id);
	
	public User saveUserInfo(WeiChartHttpUser httpUser);

	public ScoreExchangeRecord findTop1RecordByUserId(String userId);
	
	public Application findTop1ApplicationByUserId(String userId);

	public Page<Application> findAllMyApplication(DataRequest request,
			String userId);

	public User findByOpenid(String openid);
	
	public Page<Activity> findAllMyActivity(DataRequest request,
			String userId);
	
	public User findByUserNameAndPassWord(String userName,String password);
}
